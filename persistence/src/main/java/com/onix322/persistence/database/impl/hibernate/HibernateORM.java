package com.onix322.persistence.database.impl.hibernate;

import com.onix322.persistence.database.ORM;
import org.hibernate.Session;

public interface HibernateORM extends ORM {
    Session getSession();
}
