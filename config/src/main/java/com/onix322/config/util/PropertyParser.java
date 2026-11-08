package com.onix322.config.util;

import jakarta.inject.Singleton;

@Singleton
public class PropertyParser {

    public <T> T parse(String property, Class<T> targetType) {
        if (property == null || property.isBlank() && targetType != String.class) {
            return null;
        }

        String trimmed = property.trim();

        if (targetType == String.class) {
            return targetType.cast(trimmed);
        }
        if (targetType == Integer.class) {
            return targetType.cast(Integer.valueOf(trimmed));
        }
        if (targetType == Long.class) {
            return targetType.cast(Long.valueOf(trimmed));
        }
        if (targetType == Boolean.class) {
            if ("true".equalsIgnoreCase(trimmed) || "false".equalsIgnoreCase(trimmed)) {
                return targetType.cast(Boolean.valueOf(trimmed));
            }
            throw new IllegalArgumentException("Invalid boolean value: " + property);
        }
        if (targetType == Double.class) {
            return targetType.cast(Double.valueOf(trimmed));
        }

        throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
    }
}
