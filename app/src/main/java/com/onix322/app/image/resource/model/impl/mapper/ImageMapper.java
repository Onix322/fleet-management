package com.onix322.app.image.resource.model.impl.mapper;

import com.onix322.app.image.resource.model.ImageAbstract;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.model.impl.dto.ImageProfileDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {

    public ImageProfileDto toImageProfileDto(ImageProfile image) {
        return ImageProfileDto.builder()
                .id(image.getId())
                .build();
    }

    public Collection<ImageProfileDto> toImageProfileDto(Collection<ImageProfile> image) {
        return image.stream()
                .map(this::toImageProfileDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
