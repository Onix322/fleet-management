package com.onix322.app.report.guice;

import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.report.resource.model.Report;
import com.onix322.app.report.resource.model.ReportAbstract;
import com.onix322.app.report.resource.model.impl.DamageReport;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.repository.ReportRepository;
import com.onix322.app.vehicle.resource.model.VehicleMotorized;
import com.onix322.app.vehicle.resource.model.VehicleNonMotorized;
import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;

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

    @Provides
    public ReportRepository<InspectionReport> provideInspectionReportRepository(Injector injector) {
        ReportRepository<InspectionReport> reportRepository = new ReportRepository<>(InspectionReport.class);
        injector.injectMembers(reportRepository);
        return reportRepository;
    }
    @Provides
    public ReportRepository<DamageReport> provideDamageReportRepository(Injector injector) {
        ReportRepository<DamageReport> reportRepository = new ReportRepository<>(DamageReport.class);
        injector.injectMembers(reportRepository);
        return reportRepository;
    }

    @Provides
    public ReportRepository<ReportAbstract> provideReportRepository(Injector injector) {
        ReportRepository<ReportAbstract> reportRepository = new ReportRepository<>(ReportAbstract.class);
        injector.injectMembers(reportRepository);
        return reportRepository;
    }
}
