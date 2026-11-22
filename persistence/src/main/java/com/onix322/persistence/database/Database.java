package com.onix322.persistence.database;

import javax.sql.DataSource;

public interface Database {
    DataSource getDataSource();
}
