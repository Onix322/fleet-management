package com.onix322.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.onix322.config.Configuration;
import com.onix322.config.impl.ConfigurationImpl;
import com.onix322.config.util.ConfigurationLoader;
import jakarta.inject.Singleton;

@Singleton
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        ConfigurationLoader loader = new ConfigurationLoader();
        loader.loadProperties();
        Names.bindProperties(binder(), loader.getProperties());

        bind(ConfigurationLoader.class).toInstance(loader);
        bind(Configuration.class).to(ConfigurationImpl.class);
    }
}
