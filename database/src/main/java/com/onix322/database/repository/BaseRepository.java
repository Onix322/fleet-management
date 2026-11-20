package com.onix322.database.repository;

import com.onix322.database.ORM;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * Generic persistence operations built on top of the {@link ORM} transaction boundary.
 *
 * <p>Feature repositories extend this class and supply the entity type. Every method runs in
 * its own transaction, because it delegates to {@link ORM#execute} or
 * {@link ORM#executeWithResult}.</p>
 *
 * @param <T>  entity type
 * @param <ID> identifier type
 */

public class BaseRepository<T, ID> {

    @Inject
    protected ORM orm;
    @Getter
    private final Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.type = type;
    }

    public Optional<T> find(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return orm.executeWithResult(em -> Optional.ofNullable(em.find(type, id)));
    }

    public List<T> findAll() {
        return orm.executeWithResult(em -> {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root);
            return em.createQuery(query).getResultList();
        });
    }

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        return orm.executeWithResult(em -> em.merge(entity));
    }

    public Boolean delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return orm.executeWithResult(em -> {
            T entity = em.find(type, id);
            if (entity == null) return false;

            em.remove(entity);
            return true;
        });
    }

    public long count() {
        return orm.executeWithResult(em -> {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<T> root = query.from(type);
            query.select(builder.count(root));
            return em.createQuery(query).getSingleResult();
        });
    }

    public boolean exists(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return orm.executeWithResult(em -> em.find(type, id) != null);
    }
}
