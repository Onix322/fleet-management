package com.onix322.app.image.resource.service;

import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.image.resource.repository.ImageRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class ImageService<T extends ImageAbstract> {

    private final ImageRepository<T> imageRepository;

    @Inject
    public ImageService(ImageRepository<T> imageRepository) {
        this.imageRepository = imageRepository;
    }

    public T create(T image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        return imageRepository.save(image);
    }

    public T getById(UUID id) {
        Optional<T> image = imageRepository.find(id);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with id " + id + " not found");
        }
        return image.get();
    }

    public List<T> getAll() {
        return imageRepository.findAll();
    }

    public Boolean delete(UUID id) {
        return imageRepository.delete(id);
    }

    public T update(UUID id, T image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        image.setId(id);
        return imageRepository.save(image);
    }
}
