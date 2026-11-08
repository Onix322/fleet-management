package com.onix322.config.impl;

import com.onix322.config.Configuration;
import com.onix322.config.util.ConfigurationLoader;
import com.onix322.config.util.PropertyParser;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.Properties;

@AllArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class ConfigurationImpl implements Configuration {

    private ConfigurationLoader configuration;
    private PropertyParser propertyParser;

    @Override
    public <T> T getProperty(String key, Class<T> type) {
        String property = configuration.getRawProperty(key);
        return propertyParser.parse(property, type);
    }

    @Override
    public Properties getProperties(String key) {
        return configuration.getProperties();
    }
}
