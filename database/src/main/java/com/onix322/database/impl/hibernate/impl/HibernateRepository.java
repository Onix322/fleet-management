package com.onix322.database.impl.hibernate.impl;

import com.onix322.database.repository.BaseRepository;

public abstract class HibernateRepository<T, ID> extends BaseRepository<T, ID> {
    protected HibernateRepository(Class<T> type) {
        super(type);
    }
}
