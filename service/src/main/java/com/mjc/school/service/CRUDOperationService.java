package com.mjc.school.service;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;

import java.util.List;

/**
 * The interface Crud operation service.
 *
 * @param <T> the type parameter
 */
public interface CRUDOperationService<T> {
    /**
     * Create entity.
     *
     * @param entity the entity
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws ServiceException            the service exception
     */
    public boolean create(T entity)
            throws IncorrectParameterException, ServiceException;

    /**
     * Delete entity by id.
     *
     * @param id the entity id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteById(long id)
            throws ServiceException, IncorrectParameterException;

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean update(T entity)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find all entity.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<T> findAll()
            throws ServiceException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public T findById(long id)
            throws ServiceException, IncorrectParameterException;
}