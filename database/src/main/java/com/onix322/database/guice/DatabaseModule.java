package com.onix322.database.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.database.Database;
import com.onix322.database.ORM;
import com.onix322.database.impl.HibernateORM;
import com.onix322.database.impl.PostgresSQL;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Database.class).to(PostgresSQL.class).asEagerSingleton();
        bind(ORM.class).to(HibernateORM.class).asEagerSingleton();

        Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {});
    }
}
