package com.onix322.app.damage.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.damage.resource.model.DamageAbstract;
import com.onix322.app.damage.resource.model.impl.VehicleDamage;

@Singleton
public class DamageModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {});

        entities.addBinding().toInstance(DamageAbstract.class);
        entities.addBinding().toInstance(VehicleDamage.class);
    }
}
