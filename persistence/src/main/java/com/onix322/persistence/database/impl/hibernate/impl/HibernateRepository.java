package com.onix322.persistence.database.impl.hibernate.impl;

import com.onix322.persistence.repository.BaseRepository;

public abstract class HibernateRepository<T, ID> extends BaseRepository<T, ID> {
    protected HibernateRepository(Class<T> type) {
        super(type);
    }
}
