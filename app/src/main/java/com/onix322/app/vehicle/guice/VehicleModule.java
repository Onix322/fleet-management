package com.onix322.app.vehicle.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.vehicle.resource.model.impl.Van;
import jakarta.inject.Singleton;

@Singleton
public class VehicleModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });

        bindEntities(entities);
    }

    private void bindEntities(Multibinder<Class<?>> entities) {
        entities.addBinding().toInstance(Van.class);
    }
}
