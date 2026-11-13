package com.onix322.app.damage.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ImageDimension <T> {
    private T width;
    private T height;
}
