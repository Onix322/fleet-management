package com.onix322.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public interface Server {
    void start();

    void stop();

    void kill();

    static void launch(Module... modules) {
        Injector injector = Guice.createInjector(modules);
        Server server = injector.getInstance(Server.class);

        try {
            server.start();
        } catch (Exception e) {
            server.kill();
            throw new RuntimeException(e);
        }
    }
}
