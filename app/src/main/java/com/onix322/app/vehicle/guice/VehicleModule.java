package com.onix322.app.vehicle.guice;

import com.google.inject.AbstractModule;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.app.vehicle.resource.model.impl.VehicleMotorized;
import com.onix322.app.vehicle.resource.model.impl.VehicleNonMotorized;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import jakarta.inject.Singleton;
import onix322.initializer.guice.InitializerModule;

import java.util.Set;

@Singleton
public class VehicleModule extends AbstractModule {

    private static final Set<Class<?>> VEHICLE_ENTITIES = Set.of(
            VehicleAbstract.class,
            VehicleMotorized.class,
            VehicleNonMotorized.class,
            Van.class);

    @Override
    protected void configure() {
        install(new InitializerModule(VEHICLE_ENTITIES, VehicleRepository.class));
    }
}
