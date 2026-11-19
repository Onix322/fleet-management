package com.onix322.app.image.resource.service;

import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.image.resource.repository.ImageRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ImageService {

    @Inject
    private ImageRepository<ImageAbstract> imageRepository;

}
