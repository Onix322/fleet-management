package com.onix322.app.vehicle.resource.repository;

import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.database.repository.BaseRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class VanRepository extends BaseRepository<Van, UUID> {
    public VanRepository() {
        super(Van.class);
    }
}
