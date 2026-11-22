package com.onix322.persistence.filter.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.onix322.persistence.filter.OpenSessionInViewFilter;
import jakarta.servlet.Filter;


public class FilterModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), Filter.class)
                .addBinding()
                .to(OpenSessionInViewFilter.class)
                .asEagerSingleton();
    }
}
