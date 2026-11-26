package com.onix322.app.vehicle.resource.model.impl.mapper;

import com.onix322.app.details.resource.model.imp.DetailsMapper;
import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.details.resource.model.imp.dto.VehicleDetailsDto;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;
import com.onix322.app.image.resource.model.impl.mapper.ImageMapper;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.*;
import com.onix322.app.vehicle.util.adnotations.MapTo;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class VehicleMapper {

    private final ImageMapper imageMapper;
    private final DetailsMapper detailsMapper;

    @Inject
    private VehicleMapper(ImageMapper imageMapper, DetailsMapper detailsMapper) {
        this.imageMapper = imageMapper;
        this.detailsMapper = detailsMapper;
    }

    public VehicleDto toVehicleDto(VehicleAbstract vehicle) {
        List<ImageProfileDto> images = imageMapper.toImageProfileDto(vehicle.getImages())
                .stream()
                .toList();

        VehicleDetailsDto detailsDto = detailsMapper.toDetailsDto(vehicle.getDetails());

        return VehicleDto.builder()
                .images(images)
                .details(detailsDto)
                .operationalStatus(vehicle.getOperationalStatus())
                .id(vehicle.getId())
                .build();
    }


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

    public VehicleMotorizedDto toVehicleMotorizedDto(Van van) {
        if (van == null) {
            return null;
        }

        List<ImageProfileDto> imagesDto = imageMapper.toImageProfileDto(van.getImages())
                .stream()
                .toList();

        VehicleDetailsDto vehicleDetailsDto = VehicleDetailsDto
                .builder()
                .registrationDate(van.getDetails().getRegistrationDate())
                .ownType(van.getDetails().getOwnType())
                .constructionYear(van.getDetails().getConstructionYear())
                .brand(van.getDetails().getBrand())
                .model(van.getDetails().getModel())
                .build();

        return VehicleMotorizedDto
                .builder()
                .details(vehicleDetailsDto)
                .id(van.getId())
                .images(imagesDto)
                .lastPTI(van.getLastPTI())
                .licensePlate(van.getLicensePlate())
                .operationalStatus(van.getOperationalStatus())
                .build();
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
        ImageProfileDto imageProfileDto = ImageProfileDto.builder()
                .id(van.getImages().getFirst().getId())
                .build();
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

