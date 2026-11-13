package com.onix322.app.vehicle.resource.service;


import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.repository.VanRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class VanService {
    private VanRepository vanRepository;

    @Transactional
    public Van create(Van van) {
        if(van.getDetails() == null || van.getImages().isEmpty()) {
            throw new IllegalArgumentException("Vehicle details or profile images cannot be null / empty");
        }
        van.getDetails().setVehicle(van);
        van.getImages().forEach(image -> image.setVehicle(van));
        return vanRepository.save(van);
    }

    public List<Van> getAll() {
        return vanRepository.findAll();
    }

    public Boolean delete(UUID id) {
        Optional<Van> van = vanRepository.find(id);
        van.ifPresent(value -> vanRepository.delete(value));
        return vanRepository.exists(id);
    }

    public Optional<Van> getById(UUID id) {
        return vanRepository.find(id);
    }

    public Van update(UUID id, Van van) {
        van.setId(id);
        return vanRepository.update(van);
    }

}