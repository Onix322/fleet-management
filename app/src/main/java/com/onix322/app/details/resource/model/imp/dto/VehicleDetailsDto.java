package com.onix322.app.details.resource.model.imp.dto;

import com.onix322.app.vehicle.util.OwnershipType;
import lombok.*;

import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VehicleDetailsDto {
    private String brand;
    private String model;
    private Date constructionYear;
    private OwnershipType ownType;
    private Date registrationDate;
}
