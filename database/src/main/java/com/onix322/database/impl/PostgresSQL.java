package com.onix322.database.impl;

import com.onix322.config.Configuration;
import com.onix322.database.Database;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PostgresSQL implements Database {

    private final Configuration configuration;

    @Override
    public DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(configuration.getProperty("database.url", String.class));
        dataSource.setUser(configuration.getProperty("database.user", String.class));
        dataSource.setPassword(configuration.getProperty("database.password", String.class));
        return dataSource;
    }
}

