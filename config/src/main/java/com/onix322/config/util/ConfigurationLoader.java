package com.onix322.config.util;

import jakarta.inject.Singleton;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

@Getter
@Singleton
public class ConfigurationLoader {
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";
    private final Properties properties = new Properties();

    public void loadProperties() {
       loadProperties(DEFAULT_PROPERTIES_FILE);
    }

    public void loadProperties(String from) {
       try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(from)) {
            if (inputStream == null) {
                throw new IllegalStateException("File not found in classpath: " + from);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load " + from, e);
        }
    }

    public String getRawProperty(String key) {
        return properties.getProperty(key);
    }

}
