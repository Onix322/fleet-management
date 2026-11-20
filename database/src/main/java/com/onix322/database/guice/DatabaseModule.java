package com.onix322.database.guice;

import com.google.inject.AbstractModule;
import com.onix322.database.Database;
import com.onix322.database.impl.hibernate.guice.HibernateModule;
import com.onix322.database.impl.postgresql.guice.PostgresSQLModule;
import com.onix322.database.impl.postgresql.impl.PostgresSQLImpl;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PostgresSQLModule());
        install(new HibernateModule());
    }
}
