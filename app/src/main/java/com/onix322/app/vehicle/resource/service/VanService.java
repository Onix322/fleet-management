package com.onix322.app.vehicle.resource.service;


import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.repository.VanRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class VanService {
    private VanRepository vanRepository;

    public Van create(Van van) {
        return vanRepository.save(van);
    }
}
