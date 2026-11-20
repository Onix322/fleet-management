package com.onix322.database.impl.hibernate.impl;

import com.onix322.config.Configuration;
import com.onix322.database.Database;
import com.onix322.database.impl.hibernate.HibernateORM;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;

import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Singleton
public class HibernateORMImpl implements HibernateORM {

    private final Database database;
    private final Configuration configuration;
    private final Set<Class<?>> entities;
    private final SessionFactory sessionFactory;
    private final ThreadLocal<Session> scopedSession = new ThreadLocal<>();

    @Inject
    public HibernateORMImpl(Database database, Configuration configuration, Set<Class<?>> entities) {
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
        return inTransaction(action);
    }

    @Override
    public void execute(Consumer<EntityManager> action) {
        inTransaction(em -> {
            action.accept(em);
            return null;
        });
    }

    @Override
    public void begin() {
        if (scopedSession.get() == null) {
            scopedSession.set(sessionFactory.openSession());
        }
    }

    @Override
    public void end() {
        Session session = scopedSession.get();
        if (session == null) {
            return;
        }
        scopedSession.remove();
        try {
            Transaction transaction = session.getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public Session getSession() {
        return scopedSession.get();
    }

    @Override
    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    /**
     * Runs the action on the session bound to the current thread, or opens a short-lived
     * session when no scope is active. An already active transaction is joined, matching
     * {@code PROPAGATION_REQUIRED} semantics.
     */
    private <R> R inTransaction(Function<EntityManager, R> action) {
        Session session = scopedSession.get();
        if (session == null) {
            return sessionFactory.fromTransaction(action::apply);
        }

        Transaction transaction = session.getTransaction();
        if (transaction.isActive()) {
            return action.apply(session);
        }

        transaction.begin();
        try {
            R result = action.apply(session);
            transaction.commit();
            return result;
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }
}
