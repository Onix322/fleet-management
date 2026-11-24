package com.onix322.app.vehicle.resource.repository;

import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.persistence.repository.BaseRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class VehicleRepository<T extends VehicleAbstract> extends BaseRepository<T, UUID> {
    @Inject
    public VehicleRepository(Class<T> type) {
        super(type);
    }
}
