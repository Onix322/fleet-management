package com.onix322.http.exception.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpExceptionMapper;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.ExceptionMapper;

public class HttpExceptionModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), Feature.class)
                .addBinding()
                .toInstance(HttpExceptionFeature.INSTANCE);
    }

    private enum HttpExceptionFeature implements Feature {
        INSTANCE;

        @Override
        public boolean configure(FeatureContext context) {
            context.register(HttpExceptionMapper.class);
            return true;
        }
    }
}
