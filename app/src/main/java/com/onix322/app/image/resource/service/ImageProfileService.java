package com.onix322.app.image.resource.service;

import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.repository.ImageRepository;
import com.onix322.app.vehicle.resource.model.impl.VehicleAbstract;
import com.onix322.app.vehicle.resource.service.VehicleService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ImageProfileService extends ImageService<ImageProfile> {

    private final VehicleService<VehicleAbstract> vehicleService;

    @Inject
    public ImageProfileService(ImageRepository<ImageProfile> imageRepository, VehicleService<VehicleAbstract> vehicleService) {
        super(imageRepository);
        this.vehicleService = vehicleService;
    }

    public ImageProfile create(ImageProfile imageProfile) {
        if (imageProfile.getVehicle() == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (imageProfile.getVehicle().getId() == null) {
            throw new IllegalArgumentException("Vehicle id cannot be null");
        }
        if (!vehicleService.exists(imageProfile.getVehicle().getId())) {
            throw new IllegalArgumentException("Vehicle does not exist");
        }
        return super.create(imageProfile);
    }
}
