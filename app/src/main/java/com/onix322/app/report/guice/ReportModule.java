package com.onix322.app.report.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;

@Singleton
public class ReportModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });

        entities.addBinding().toInstance(ReportAbstract.class);
        entities.addBinding().toInstance(InspectionReport.class);
        entities.addBinding().toInstance(DamageReport.class);
    }
}
