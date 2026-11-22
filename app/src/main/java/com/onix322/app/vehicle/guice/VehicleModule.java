package com.onix322.app.vehicle.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import com.onix322.app.vehicle.resource.model.VehicleMotorized;
import com.onix322.app.vehicle.resource.model.VehicleNonMotorized;
import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import jakarta.inject.Singleton;

@Singleton
public class VehicleModule extends AbstractModule {


    @Override
    protected void configure() {
        Multibinder<Class<?>> entities = Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });

        entities.addBinding().toInstance(VehicleAbstract.class);
        entities.addBinding().toInstance(VehicleMotorized.class);
        entities.addBinding().toInstance(VehicleNonMotorized.class);
        entities.addBinding().toInstance(Van.class);

    }

    @Provides
    public VehicleRepository<Van> providesVanRepository(Injector injector) {
        VehicleRepository<Van> repository = new VehicleRepository<>(Van.class);
        injector.injectMembers(repository);
        return repository;
    }

    @Provides
    public VehicleRepository<VehicleMotorized> providesVehicleMotorizedRepository(Injector injector) {
        VehicleRepository<VehicleMotorized> repository = new VehicleRepository<>(VehicleMotorized.class);
        injector.injectMembers(repository);
        return repository;
    }

    @Provides
    public VehicleRepository<VehicleNonMotorized> providesVehicleNonMotorizedRepository(Injector injector) {
        VehicleRepository<VehicleNonMotorized> repository = new VehicleRepository<>(VehicleNonMotorized.class);
        injector.injectMembers(repository);
        return repository;
    }

    @Provides
    public VehicleRepository<VehicleAbstract> providesVehicleAbstractRepository(Injector injector) {
        VehicleRepository<VehicleAbstract> repository = new VehicleRepository<>(VehicleAbstract.class);
        injector.injectMembers(repository);
        return repository;
    }
}
