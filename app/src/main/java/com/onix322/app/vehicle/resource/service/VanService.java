package com.onix322.app.vehicle.resource.service;

import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanReturnDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanSummaryDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanUpdateDto;
import com.onix322.app.vehicle.resource.model.impl.mapper.VanMapper;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import com.onix322.http.exception.client.BadRequestException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Singleton
public class VanService extends VehicleService<Van> {

    private final VanMapper vanMapper;

    @Inject
    public VanService(VehicleRepository<Van> vanRepository, VanMapper vanMapper) {
        super(vanRepository);
        this.vanMapper = vanMapper;
    }

    public VanReturnDto create(VanAddDto vanDto) {
        if (vanDto == null) {
            throw new BadRequestException("VanDto cannot be null");
        }
        Van mappedVan = vanMapper.toVan(vanDto);
        Van createdVan = super.create(mappedVan);
        return vanMapper.toVanDto(createdVan);
    }

    public VanReturnDto update(UUID id, VanUpdateDto vanDto) {
        if (vanDto == null || id == null) {
            throw new BadRequestException("VanDto must not be null and must have an id");
        }
        Van van = super.getById(id);
        Van updateVan = vanMapper.updateVan(vanDto, van);
        Van updatedVanDto = super.update(id, updateVan);
        return vanMapper.toVanDto(updatedVanDto);
    }

    public List<VanSummaryDto> getAllVans() {
        return super.getAll()
                .stream()
                .map(vanMapper::toVanSummaryDto)
                .toList();
    }
}
