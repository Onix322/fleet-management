package com.onix322.app.report.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Types;
import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.repository.ReportRepository;
import jakarta.inject.Singleton;
import onix322.initializer.TypeFactory;

import java.util.List;

@Singleton
public class ReportModule extends AbstractModule {

    private static final List<Class<?>> REPORT_ENTITIES = List.of(
            ReportAbstract.class,
            InspectionReport.class,
            DamageReport.class
    );

    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });

        REPORT_ENTITIES.forEach(entity -> entities.addBinding().toInstance(entity));

        Provider<TypeFactory> factory = getProvider(TypeFactory.class);
        REPORT_ENTITIES.forEach(entity -> registerRepository(entity, factory, ReportRepository.class));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> void registerRepository(Class<?> entity, Provider<TypeFactory> factory, Class<T> repository) {
        TypeLiteral key = TypeLiteral.get(Types.newParameterizedType(repository, entity));
        bind(key).toProvider(() -> factory.get().create(entity, key))
                .in(Singleton.class);
    }
}
