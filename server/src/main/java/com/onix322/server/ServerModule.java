package com.onix322.server;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.onix322.server.impl.TomcatServer;
import jakarta.inject.Singleton;
import jakarta.servlet.Filter;

@Singleton
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(TomcatServer.class);

        Multibinder.newSetBinder(binder(), Filter.class);
    }
}
