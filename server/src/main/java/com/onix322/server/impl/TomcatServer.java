package com.onix322.server.impl;

import com.google.inject.Injector;
import com.onix322.config.Configuration;
import com.onix322.server.Server;
import com.onix322.server.config.GuiceToHK2Bridge;
import com.onix322.server.config.Hk2LocatorHolder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.File;
import java.util.Set;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class TomcatServer implements Server {

    private final Tomcat tomcat;
    private final Configuration configuration;
    private final Injector injector;
    private final Hk2LocatorHolder hk2LocatorHolder;
    private final Set<Filter> filters;

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
        this.filtersConfig(context);

    }

    private void jerseyConfig(Context context) {

        ResourceConfig jerseyConfig = new ResourceConfig();

        jerseyConfig.packages("com.onix322.app");

        // Connect Guice with HK2
        jerseyConfig.register(new GuiceToHK2Bridge(injector, hk2LocatorHolder));

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

    /**
     * Registers the Guice-provided servlet filters on the context. Filters run around the
     * Jersey servlet, so they cover the whole request including entity serialization.
     */
    private void filtersConfig(Context context) {
        filters.forEach(filter -> {
            String name = filter.getClass().getName();

            FilterDef definition = new FilterDef();
            definition.setFilterName(name);
            definition.setFilter(filter);
            context.addFilterDef(definition);

            FilterMap mapping = new FilterMap();
            mapping.setFilterName(name);
            mapping.addURLPattern("/*");
            context.addFilterMap(mapping);
        });
    }
}
