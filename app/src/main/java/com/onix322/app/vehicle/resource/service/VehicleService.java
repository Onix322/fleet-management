package com.onix322.app.vehicle.resource.service;

import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class VehicleService<T extends VehicleAbstract> {

    private final VehicleRepository<T> vanRepository;

    @Inject
    public VehicleService(VehicleRepository<T> vanRepository) {
        this.vanRepository = vanRepository;
    }

    public T create(T van) {
        if (van.getDetails() == null || van.getImages().isEmpty()) {
            throw new IllegalArgumentException("Vehicle details or profile images cannot be null / empty");
        }
        return vanRepository.save(van);
    }

    public List<T> getAll() {
        return vanRepository.findAll();
    }

    public Boolean delete(UUID id) {
        return this.vanRepository.delete(id);
    }

    public T getById(UUID id) {
        Optional<T> optional = vanRepository.find(id);
        return optional.orElseThrow(() -> new NotFoundException("Vehicle not found"));
    }

    public T update(UUID id, T van) {
        if(van == null || id == null) {
            throw new IllegalArgumentException("Vehicle or id cannot be null");
        }
        van.setId(id);
        return vanRepository.save(van);
    }

    public Boolean exists(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return vanRepository.exists(id);
    }
}
