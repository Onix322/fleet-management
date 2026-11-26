package com.onix322.app.vehicle.resource.service;

import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanSummaryDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanUpdateDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VehicleMotorizedDto;
import com.onix322.app.vehicle.resource.model.impl.mapper.VehicleMapper;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Singleton
public class VanService extends VehicleService<Van> {

    private final VehicleMapper vanMapper;

    @Inject
    public VanService(VehicleRepository<Van> vanRepository, VehicleMapper vanMapper) {
        super(vanRepository);
        this.vanMapper = vanMapper;
    }

    public VehicleMotorizedDto create(VanAddDto vanDto) {
        if (vanDto == null) {
            throw new IllegalArgumentException("VanDto cannot be null");
        }
        Van mappedVan = vanMapper.toVan(vanDto);
        Van createdVan = super.create(mappedVan);
        return vanMapper.toVehicleMotorizedDto(createdVan);
    }

    public VehicleMotorizedDto update(UUID id, VanUpdateDto vanDto) {
        if (vanDto == null || id == null) {
            throw new IllegalArgumentException("VanDto must not be null and must have an id");
        }
        Van van = super.getById(id);
        Van updateVan = vanMapper.updateVan(vanDto, van);
        Van updatedVanDto = super.update(id, updateVan);
        return vanMapper.toVehicleMotorizedDto(updatedVanDto);
    }

    public List<VanSummaryDto> getAllVans() {
        return super.getAll()
                .stream()
                .map(vanMapper::toVanSummaryDto)
                .toList();
    }
}
