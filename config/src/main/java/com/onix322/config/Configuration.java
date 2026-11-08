package com.onix322.config;

import java.util.Properties;

public interface Configuration {

    <T> T getProperty(String key, Class<T> type);

    Properties getProperties(String key);


}
