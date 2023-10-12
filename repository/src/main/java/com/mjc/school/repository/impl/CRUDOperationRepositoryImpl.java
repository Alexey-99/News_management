package com.mjc.school.repository.impl;

import com.mjc.school.repository.CRUDOperationRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public abstract class CRUDOperationRepositoryImpl<T>
        implements CRUDOperationRepository<T> {
    private final EntityManager entityManager;

    protected CRUDOperationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        boolean result = false;
        T entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
            result = findById(id) == null;
        } else {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<T> findAll(int page, int size) {
        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<T> entityCriteriaQuery =
                criteriaBuilder.createQuery(getEntityClass());
        Root<T> root = entityCriteriaQuery.from(getEntityClass());
        CriteriaQuery<T> select = entityCriteriaQuery.select(root);
        return entityManager.createQuery(select)
                .setFirstResult(calcNumberFirstElement(page, size))
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<T> entityCriteriaQuery =
                criteriaBuilder.createQuery(getEntityClass());
        Root<T> root = entityCriteriaQuery.from(getEntityClass());
        CriteriaQuery<T> select = entityCriteriaQuery.select(root);
        return entityManager.createQuery(select)
                .getResultList();
    }

    @Override
    public T findById(long id) {
        return entityManager.find(
                getEntityClass(), id);
    }

    protected int calcNumberFirstElement(int page, int size) {
        int numberStartElement = size * (page - 1);
        return numberStartElement >= 0 ?
                numberStartElement : 1;
    }

    protected abstract Class<T> getEntityClass();
}