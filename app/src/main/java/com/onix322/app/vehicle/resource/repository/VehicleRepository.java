package com.onix322.app.vehicle.resource.repository;

import com.onix322.app.image.resource.model.impl.ImageProfile;
import com.onix322.database.repository.BaseRepository;
import jakarta.inject.Singleton;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Singleton
public class VehicleRepository<T> extends BaseRepository<T, UUID> {
    public VehicleRepository(Class<T> type) {
        super(type);
    }
}
