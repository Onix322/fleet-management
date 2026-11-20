package com.onix322.database.impl.hibernate;

import com.onix322.database.ORM;
import org.hibernate.Session;

public interface HibernateORM extends ORM {
    Session getSession();
}
