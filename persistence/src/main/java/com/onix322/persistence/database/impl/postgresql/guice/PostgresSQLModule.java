package com.onix322.persistence.database.impl.postgresql.guice;

import com.google.inject.AbstractModule;
import com.onix322.persistence.database.Database;
import com.onix322.persistence.database.impl.postgresql.PostgresSQLDatabase;
import com.onix322.persistence.database.impl.postgresql.impl.PostgresSQLImpl;

public class PostgresSQLModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PostgresSQLImpl.class).asEagerSingleton();
        bind(Database.class).to(PostgresSQLImpl.class).asEagerSingleton();
        bind(PostgresSQLDatabase.class).to(PostgresSQLImpl.class).asEagerSingleton();
    }

}
