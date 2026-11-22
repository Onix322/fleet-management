package com.onix322.launcher;

import com.google.inject.AbstractModule;
import com.onix322.app.guice.AppModule;
import com.onix322.config.guice.ConfigModule;
import com.onix322.persistence.guice.PersistenceModule;
import com.onix322.server.Server;
import com.onix322.server.guice.ServerModule;

public class LauncherModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ServerModule());
        install(new ConfigModule());
        install(new PersistenceModule());
        install(new AppModule());
    }

    public static void main(String[] args) {
        Server.launch(new LauncherModule());
    }
}
