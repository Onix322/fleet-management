package com.onix322.app.image.resource.repository;

import com.onix322.database.repository.BaseRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class ImageRepository<T> extends BaseRepository<T, UUID> {
    protected ImageRepository(Class<T> type) {
        super(type);
    }
}
