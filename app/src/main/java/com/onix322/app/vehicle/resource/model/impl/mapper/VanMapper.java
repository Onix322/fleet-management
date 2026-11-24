package com.onix322.app.vehicle.resource.model.impl.mapper;

import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.details.resource.model.imp.dto.VehicleDetailsDto;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanReturnDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanSummaryDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanUpdateDto;
import com.onix322.app.vehicle.util.adnotations.MapTo;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@NoArgsConstructor(onConstructor_ = @Inject)
public class VanMapper {

    public Van toVan(VanAddDto vanDto) {

        Van van = new Van();
        van.setLastPTI(vanDto.getLastPTI());
        van.setVin(vanDto.getVin());
        van.setLicensePlate(vanDto.getLicensePlate());
        van.setOperationalStatus(vanDto.getOperationalStatus());

        VehicleDetails details = new VehicleDetails();
        details.setBrand(vanDto.getDetails().getBrand());
        details.setModel(vanDto.getDetails().getModel());
        details.setConstructionYear(vanDto.getDetails().getConstructionYear());
        details.setOwnType(vanDto.getDetails().getOwnType());
        details.setRegistrationDate(vanDto.getDetails().getRegistrationDate());
        details.setVehicle(van);

        List<ImageProfile> images = vanDto.getImages()
                .stream()
                .map(image -> {
                    ImageProfile imageProfile = new ImageProfile();
                    imageProfile.setVehicle(van);
                    return imageProfile;
                }).collect(Collectors.toCollection(ArrayList::new));

        van.setDetails(details);
        van.setImages(images);

        return van;
    }

    public VanReturnDto toVanDto(Van van) {
        if (van == null) {
            return null;
        }

        VanReturnDto dto = new VanReturnDto();
        dto.setId(van.getId());
        dto.setLastPTI(van.getLastPTI());
        dto.setVin(van.getVin());
        dto.setLicensePlate(van.getLicensePlate());
        dto.setOperationalStatus(van.getOperationalStatus());

        if (van.getDetails() != null) {
            var entityDetails = van.getDetails();
            VehicleDetailsDto detailsDto = new VehicleDetailsDto();
            detailsDto.setBrand(entityDetails.getBrand());
            detailsDto.setModel(entityDetails.getModel());
            detailsDto.setConstructionYear(entityDetails.getConstructionYear());
            detailsDto.setOwnType(entityDetails.getOwnType());
            detailsDto.setRegistrationDate(entityDetails.getRegistrationDate());
            dto.setDetails(detailsDto);
        }

        List<ImageProfileDto> imagesDto = new ArrayList<>();
        for (var imageProfile : van.getImages()) {
            if (imageProfile == null) {
                continue;
            }
            ImageProfileDto imageDto = new ImageProfileDto();
            imagesDto.add(imageDto);
        }
        dto.setImages(imagesDto);

        return dto;
    }

    public Van updateVan(VanUpdateDto vanDto, Van existingVan) {
        if (vanDto == null || existingVan == null) {
            throw new IllegalArgumentException("VanDto and existingVan must not be null");
        }

        for (Field field : vanDto.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }
            if ("id".equals(field.getName())) {
                continue;
            }

            try {
                field.setAccessible(true);
                Object value = field.get(vanDto);
                if (value == null) {
                    continue;
                }

                Field targetField = resolveTargetField(field, existingVan.getClass());
                targetField.setAccessible(true);
                targetField.set(existingVan, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException(
                        "Failed to map field '" + field.getName() + "' from " + vanDto.getClass().getSimpleName(),
                        e
                );
            }
        }

        return existingVan;
    }

    public VanSummaryDto toVanSummaryDto(Van van) {
        ImageProfileDto imageProfileDto = new ImageProfileDto();
        imageProfileDto.setId(van.getImages().getFirst().getId());
        return new VanSummaryDto(
                van.getId(),
                van.getLicensePlate(),
                van.getVin(),
                van.getDetails().getOwnType(),
                van.getDetails().getTotalMilage(),
                van.getOperationalStatus(),
                imageProfileDto
        );
    }

    private Field resolveTargetField(Field dtoField, Class<?> defaultTargetClass) throws NoSuchFieldException {
        String targetFieldName = dtoField.getName();
        Class<?> targetClass = defaultTargetClass;

        if (dtoField.isAnnotationPresent(MapTo.class)) {
            MapTo mapTo = dtoField.getAnnotation(MapTo.class);
            if (!mapTo.toField().isBlank()) {
                targetFieldName = mapTo.toField();
            }
            if (mapTo.foundIn() != Void.class) {
                targetClass = mapTo.foundIn();
            }
        }

        return targetClass.getDeclaredField(targetFieldName);
    }
}
