package com.onix322.app.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import jakarta.servlet.Filter;

public class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), Filter.class)
                .addBinding()
                .to(OpenSessionInViewFilter.class)
                .asEagerSingleton();
    }
}
