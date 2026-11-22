package com.onix322.persistence.database.guice;

import com.google.inject.AbstractModule;
import com.onix322.persistence.database.impl.hibernate.guice.HibernateModule;
import com.onix322.persistence.database.impl.postgresql.guice.PostgresSQLModule;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PostgresSQLModule());
        install(new HibernateModule());
    }
}
