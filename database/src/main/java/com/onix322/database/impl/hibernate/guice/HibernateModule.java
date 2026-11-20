package com.onix322.database.impl.hibernate.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.onix322.database.ORM;
import com.onix322.database.impl.hibernate.HibernateORM;
import com.onix322.database.impl.hibernate.impl.HibernateORMImpl;

public class HibernateModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(HibernateORMImpl.class).asEagerSingleton();
        bind(ORM.class).to(HibernateORMImpl.class).asEagerSingleton();
        bind(HibernateORM.class).to(HibernateORMImpl.class).asEagerSingleton();

        Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {
        });
    }
}
