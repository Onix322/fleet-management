package com.onix322.database.impl;

import com.onix322.config.Configuration;
import com.onix322.database.Database;
import com.onix322.database.ORM;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;

import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Singleton
public class HibernateORM implements ORM {

    private final Database database;
    private final Configuration configuration;
    private final Set<Class<?>> entities;
    private final SessionFactory sessionFactory;

    @Inject
    public HibernateORM(Database database, Configuration configuration, Set<Class<?>> entities) {
        this.database = database;
        this.configuration = configuration;
        this.entities = entities;
        this.sessionFactory = init();
    }

    private SessionFactory init() {
        org.hibernate.cfg.Configuration hibernateConfig = new org.hibernate.cfg.Configuration();

        Properties settings = new Properties();
        settings.put(AvailableSettings.JAKARTA_NON_JTA_DATASOURCE, database.getDataSource());
        settings.put(AvailableSettings.DIALECT, configuration.getProperty("hibernate.dialect", String.class));
        settings.put(AvailableSettings.HBM2DDL_AUTO, configuration.getProperty("hibernate.hbm2ddl.auto", String.class));
        settings.put(AvailableSettings.SHOW_SQL, configuration.getProperty("hibernate.show_sql", Boolean.class));
        settings.put(AvailableSettings.FORMAT_SQL, configuration.getProperty("hibernate.format_sql", Boolean.class));

        hibernateConfig.setProperties(settings);

        entities.forEach(hibernateConfig::addAnnotatedClass);

        return hibernateConfig.buildSessionFactory();
    }

    @Override
    public <R> R executeWithResult(Function<EntityManager, R> action) {
        return sessionFactory.fromTransaction(action::apply);
    }

    @Override
    public void execute(Consumer<EntityManager> action) {
        sessionFactory.inTransaction(action::accept);
    }

    @Override
    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
