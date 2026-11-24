package com.onix322.app.vehicle.resource.model.impl.dto;

import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.vehicle.util.OperationalStatus;
import com.onix322.app.vehicle.util.OwnershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VanSummaryDto {
    UUID id;
    String licensePlate;
    String vin;
    OwnershipType ownershipType;
    Long totalMileage;
    OperationalStatus operationalStatus;
    ImageProfileDto imageProfileDto;
}
