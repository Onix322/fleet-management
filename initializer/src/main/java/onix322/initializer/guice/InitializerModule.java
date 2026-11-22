package onix322.initializer.guice;

import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Types;
import onix322.initializer.TypeFactory;

import java.util.List;
import java.util.Set;

public class InitializerModule extends AbstractModule {

    private final Set<Class<?>> entities;
    private final Class<?> repository;

    public InitializerModule(Set<Class<?>> entities, Class<?> repository) {
        this.entities = entities;
        this.repository = repository;
    }

    @Override
    protected void configure() {
        Multibinder<Class<?>> set = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {});
        entities.forEach(entity -> set.addBinding().toInstance(entity));

        Provider<TypeFactory> factory = getProvider(TypeFactory.class);
        entities.forEach(entity -> register(entity, factory));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void register(Class<?> entity, Provider<TypeFactory> factory) {
        TypeLiteral key = TypeLiteral.get(Types.newParameterizedType(repository, entity));
        bind(key).toProvider(() -> factory.get().create(entity, key)).in(Singleton.class);
    }
}
