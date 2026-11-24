package com.onix322.app.vehicle.resource.model.impl.dto;

import com.onix322.app.details.resource.model.imp.dto.VehicleDetailsDto;
import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.vehicle.resource.model.impl.VehicleMotorized;
import com.onix322.app.vehicle.util.adnotations.MapTo;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VanUpdateDto {
    private UUID id;
    private String vin;
    private Date lastPTI;
    @MapTo(
            toField = "licensePlate",
            foundIn = VehicleMotorized.class
    )
    private String licensePlate;
    private VehicleDetailsDto details;
    private List<ImageProfileDto> images;
}
