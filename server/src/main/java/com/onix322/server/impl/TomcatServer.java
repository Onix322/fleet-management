package com.onix322.server.impl;

import com.google.inject.Injector;
import com.onix322.config.Configuration;
import com.onix322.server.Server;
import com.onix322.server.config.GuiceHK2Bridge;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.File;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class TomcatServer implements Server {

    private final Tomcat tomcat;
    private final Configuration configuration;
    private final Injector injector;

    @Override
    public void start() {
        this.tomcatConfig(tomcat);
        tomcat.getConnector();
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            tomcat.stop();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void kill() {
        try {
            tomcat.stop();
            tomcat.destroy();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    private void tomcatConfig(Tomcat server) {
        Integer port = configuration.getProperty("server.port", Integer.class);
        String baseDir = configuration.getProperty("server.base.dir", String.class);
        String contextPath = configuration.getProperty("server.context.path", String.class);


        int resolvedPort = port == null || port == 0 ? 8080 : port;
        server.setPort(resolvedPort);

        String resolvedBaseDir = baseDir.isBlank()
                ? System.getProperty("java.io.tmpdir")
                : baseDir;
        server.setBaseDir(resolvedBaseDir);

        String docBase = baseDir.isBlank()
                ? null
                : new File(baseDir).getAbsolutePath();


        Context context = server.addContext(contextPath, docBase);

        this.jerseyConfig(context);

    }

    private void jerseyConfig(Context context) {

        ResourceConfig jerseyConfig = new ResourceConfig();

        jerseyConfig.packages("com.onix322.app");

        // Connect Guice with HK2
        jerseyConfig.register(new GuiceHK2Bridge(injector));

        // Register Jersey Servlet
        Wrapper jerseyServlet = Tomcat.addServlet(
                context,
                "jersey",
                new ServletContainer(jerseyConfig)
        );

        jerseyServlet.setLoadOnStartup(1);

        String apiPath = configuration.getProperty("server.api.path", String.class);

        // Map REST API
        context.addServletMapping(apiPath, "jersey");
    }
}
