package com.mjc.school.service;

import com.mjc.school.exception.ServiceException;

import java.util.List;

public interface CRUDOperationService<T> {
    boolean create(T entity);

    boolean deleteById(long id);

    T update(T entity)
            throws ServiceException;

    List<T> findAll(int page, int size)
            throws ServiceException;

    List<T> findAll();

    T findById(long id)
            throws ServiceException;
}