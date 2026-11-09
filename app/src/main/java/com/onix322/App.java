package com.onix322;

import com.google.inject.AbstractModule;
import com.onix322.app.image.guice.ImageModule;
import com.onix322.context.ApplicationContext;
import com.onix322.server.Server;
import com.onix322.app.vehicle.guice.VehicleModule;

public class App extends AbstractModule {
    @Override
    protected void configure() {
        // Add modules (plugins)
        install(new VehicleModule());
        install(new ImageModule());
    }

    public static void main(String[] args) {
        Server.launch(ApplicationContext.getInstance(), new App());
    }
}