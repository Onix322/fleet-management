package com.onix322.app.vehicle.resource.model.impl.dto;

import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.vehicle.util.OperationalStatus;
import com.onix322.app.vehicle.util.OwnershipType;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VanSummaryDto {
    UUID id;
    String licensePlate;
    String vin;
    OwnershipType ownershipType;
    Long totalMileage;
    OperationalStatus operationalStatus;
    ImageProfileDto imageProfileDto;
}
