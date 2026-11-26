package com.onix322.app.report.resource.model.impl.mapper;

import com.onix322.app.report.resource.model.impl.dao.ReportInspection;
import com.onix322.app.report.resource.model.impl.dto.ReportDto;
import com.onix322.app.vehicle.resource.model.impl.mapper.VehicleMapper;
import jakarta.inject.Inject;

public class ReportMapper {

    private final VehicleMapper vehicleMapper;

    @Inject
    public ReportMapper(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    public ReportDto toReportDto(ReportInspection report) {

        return ReportDto.builder()
                .details(report.getDetails())
                .images(report.getImages())
                .vehicle(vehicleMapper.toVehicleDto(report.getVehicle()))
                .build();
    }
}
