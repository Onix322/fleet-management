package com.onix322.app.details.resource.model.imp.dto;

import com.onix322.app.vehicle.util.OwnershipType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@Data
public class VehicleDetailsDto {
    private String brand;
    private String model;
    private Date constructionYear;
    private OwnershipType ownType;
    private Date registrationDate;
}
