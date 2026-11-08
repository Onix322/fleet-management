package com.onix322.server;

import com.google.inject.AbstractModule;
import com.onix322.server.impl.TomcatServer;
import jakarta.inject.Singleton;

@Singleton
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(TomcatServer.class);
    }
}
