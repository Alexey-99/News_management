package com.mjc.school.repository;

import com.mjc.school.exception.RepositoryException;

import java.util.List;

/**
 * The interface Cud operation.
 *
 * @param <T> the type parameter
 */
public interface CRUDOperationRepository<T> {
    /**
     * Create entity.
     *
     * @param entity the entity
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean create(T entity) throws RepositoryException;

    /**
     * Delete entity by id.
     *
     * @param id the id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteById(long id) throws RepositoryException;

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean update(T entity) throws RepositoryException;

    /**
     * Find all entity.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<T> findAll() throws RepositoryException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the t
     * @throws RepositoryException the repository exception
     */
    public T findById(long id) throws RepositoryException;
}