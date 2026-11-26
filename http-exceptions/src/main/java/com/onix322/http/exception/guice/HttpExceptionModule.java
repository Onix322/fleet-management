package com.onix322.http.exception.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.onix322.http.exception.HttpExceptionMapper;

public class HttpExceptionModule extends AbstractModule {

    @Provides
    @Singleton
    public HttpExceptionMapper provideHttpExceptionMapper() {
        return new HttpExceptionMapper();
    }
}
