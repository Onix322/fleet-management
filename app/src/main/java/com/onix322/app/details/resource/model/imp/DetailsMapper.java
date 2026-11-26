package com.onix322.app.details.resource.model.imp;

import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.details.resource.model.imp.dto.VehicleDetailsDto;

public class DetailsMapper {

    public VehicleDetailsDto toDetailsDto(VehicleDetails details) {
        return VehicleDetailsDto.builder()
                .brand(details.getBrand())
                .constructionYear(details.getConstructionYear())
                .model(details.getModel())
                .ownType(details.getOwnType())
                .registrationDate(details.getRegistrationDate())
                .build();
    }

}
