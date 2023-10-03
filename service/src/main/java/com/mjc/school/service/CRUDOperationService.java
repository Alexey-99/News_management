package com.mjc.school.service;

import com.mjc.school.exception.ServiceException;

import java.util.List;

public interface CRUDOperationService<T> {
    public boolean create(T entity)
            throws ServiceException;

    public boolean deleteById(long id)
            throws ServiceException;

    public boolean update(T entity)
            throws ServiceException;

    public List<T> findAll()
            throws ServiceException;

    public T findById(long id)
            throws ServiceException;
}