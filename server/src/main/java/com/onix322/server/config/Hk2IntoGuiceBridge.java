package com.onix322.server.config;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import jakarta.inject.Qualifier;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.guice.bridge.api.HK2Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Injects HK2 services into Guice-created objects. Fields annotated with {@link HK2Inject} are
 * resolved from the Jersey {@link org.glassfish.hk2.api.ServiceLocator} at injection time, because
 * that locator does not exist yet when the Guice injector is created.
 */
public class Hk2IntoGuiceBridge extends AbstractModule {

    private final Hk2LocatorHolder holder = new Hk2LocatorHolder();

    @Override
    protected void configure() {
        bind(Hk2LocatorHolder.class).toInstance(holder);
        bindListener(Matchers.any(), new Hk2TypeListener(holder));
    }

    private static final class Hk2TypeListener implements TypeListener {

        private final Hk2LocatorHolder holder;

        private Hk2TypeListener(Hk2LocatorHolder holder) {
            this.holder = holder;
        }

        @Override
        public <I> void hear(TypeLiteral<I> literal, TypeEncounter<I> encounter) {
            Set<String> seen = new HashSet<>();
            for (Class<?> type = literal.getRawType(); type != null; type = type.getSuperclass()) {
                for (Field field : type.getDeclaredFields()) {
                    if (!seen.add(field.getName())) {
                        continue;
                    }
                    if (field.isAnnotationPresent(HK2Inject.class)) {
                        encounter.register(new Hk2FieldInjector<>(holder, field));
                    }
                }
            }
        }
    }

    private static final class Hk2FieldInjector<T> implements MembersInjector<T> {

        private final Hk2LocatorHolder holder;
        private final Field field;
        private final Annotation[] qualifiers;

        private Hk2FieldInjector(Hk2LocatorHolder holder, Field field) {
            this.holder = holder;
            this.field = field;
            this.field.setAccessible(true);
            List<Annotation> found = new ArrayList<>();
            for (Annotation annotation : field.getAnnotations()) {
                Class<? extends Annotation> type = annotation.annotationType();
                if (type.isAnnotationPresent(Qualifier.class)
                        || type.isAnnotationPresent(BindingAnnotation.class)) {
                    found.add(annotation);
                }
            }
            this.qualifiers = found.toArray(new Annotation[0]);
        }

        @Override
        public void injectMembers(T instance) {
            ServiceHandle<?> handle = holder.get().getServiceHandle(field.getGenericType(), qualifiers);
            if (handle == null) {
                throw new IllegalStateException("No HK2 service found for " + field);
            }
            try {
                field.set(instance, handle.getService());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot inject HK2 service into " + field, e);
            }
        }
    }
}
