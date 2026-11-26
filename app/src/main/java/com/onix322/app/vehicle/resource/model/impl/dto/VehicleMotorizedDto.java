package com.onix322.app.vehicle.resource.model.impl.dto;

import com.onix322.app.details.resource.model.imp.dto.VehicleDetailsDto;
import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.vehicle.util.OperationalStatus;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VehicleMotorizedDto {
    private UUID id;
    private String vin;
    private Date lastPTI;
    private String licensePlate;
    private VehicleDetailsDto details;
    private OperationalStatus operationalStatus;
    private List<ImageProfileDto> images;
}
