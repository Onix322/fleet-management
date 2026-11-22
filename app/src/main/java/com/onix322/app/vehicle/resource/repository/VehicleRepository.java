package com.onix322.app.vehicle.resource.repository;

import com.onix322.persistence.repository.BaseRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class VehicleRepository<T> extends BaseRepository<T, UUID> {
    public VehicleRepository(Class<T> type) {
        super(type);
    }
}
