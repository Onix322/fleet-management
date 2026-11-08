package com.onix322.server.config;


import com.google.inject.Injector;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import lombok.RequiredArgsConstructor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.InjectionManagerProvider;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

@RequiredArgsConstructor
public class GuiceHK2Bridge implements Feature {

    private final Injector injector;

    @Override
    public boolean configure(FeatureContext context) {
        InjectionManager injectionManager = InjectionManagerProvider.getInjectionManager(context);
        ServiceLocator serviceLocator = injectionManager.getInstance(ServiceLocator.class);
        GuiceBridge.getGuiceBridge()
                .initializeGuiceBridge(serviceLocator);

        GuiceIntoHK2Bridge bridge =
                serviceLocator.getService(GuiceIntoHK2Bridge.class);

        bridge.bridgeGuiceInjector(injector);
        return true;
    }
}
