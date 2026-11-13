package com.onix322.database.repository;

import com.onix322.database.ORM;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

public abstract class BaseRepository<T, ID> {

    @Inject
    protected ORM orm;
    private final Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.type = type;
    }

    public Optional<T> find(ID id) {
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
        return orm.executeWithResult(em -> {
            em.merge(entity);
            return entity;
        });
    }

    public T update(T entity) {
        return orm.executeWithResult(em -> em.merge(entity));
    }

    public void delete(T entity) {
        orm.execute(em -> em.remove(em.contains(entity) ? entity : em.merge(entity)));
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
        return orm.executeWithResult(em -> em.find(type, id) != null);
    }
}
