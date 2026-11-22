package com.onix322.persistence.guice;

import com.google.inject.AbstractModule;
import com.onix322.persistence.database.guice.DatabaseModule;
import com.onix322.persistence.filter.guice.FilterModule;

public class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DatabaseModule());
        install(new FilterModule());
    }
}
