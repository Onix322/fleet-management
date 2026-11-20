package com.onix322.app.vehicle.resource.service;

import com.onix322.app.vehicle.resource.model.VehicleAbstract;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class VehicleService<T extends VehicleAbstract> {

    private VehicleRepository<T> vanRepository;

    public T create(T van) {
        if (van.getDetails() == null || van.getImages().isEmpty()) {
            throw new IllegalArgumentException("Vehicle details or profile images cannot be null / empty");
        }
        van.getDetails().setVehicle(van);
        van.getImages().forEach(image -> image.setVehicle(van));
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
        van.setId(id);
        return vanRepository.save(van);
    }
}
