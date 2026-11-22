package com.onix322.app.report.guice;

import com.google.inject.AbstractModule;
import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.repository.ReportRepository;
import jakarta.inject.Singleton;
import onix322.initializer.guice.InitializerModule;

import java.util.Set;

@Singleton
public class ReportModule extends AbstractModule {

    private static final Set<Class<?>> REPORT_ENTITIES = Set.of(
            ReportAbstract.class,
            InspectionReport.class,
            DamageReport.class
    );

    @Override
    protected void configure() {
        install(new InitializerModule(REPORT_ENTITIES, ReportRepository.class));
    }
}
