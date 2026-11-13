package com.onix322.app.details.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.details.resource.model.DetailsAbstract;
import com.onix322.app.details.resource.model.imp.DamageDetails;
import com.onix322.app.details.resource.model.imp.ImageDetails;
import com.onix322.app.details.resource.model.imp.ReportDetails;
import com.onix322.app.details.resource.model.imp.VehicleDetails;
import com.onix322.app.report.resource.model.ReportAbstract;

public class DetailsModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });

        entities.addBinding().toInstance(DetailsAbstract.class);
        entities.addBinding().toInstance(ImageDetails.class);
        entities.addBinding().toInstance(VehicleDetails.class);
        entities.addBinding().toInstance(DamageDetails.class);
        entities.addBinding().toInstance(ReportDetails.class);
    }
}
