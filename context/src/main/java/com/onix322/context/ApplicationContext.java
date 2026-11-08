package com.onix322.context;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.onix322.config.guice.ConfigModule;
import com.onix322.database.guice.DatabaseModule;
import com.onix322.server.ServerModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationContext extends AbstractModule {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    @Override
    protected void configure() {
        install(new ServerModule());
        install(new ConfigModule());
        install(new DatabaseModule());
    }
}
