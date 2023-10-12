package com.mjc.school.service;

import com.mjc.school.exception.ServiceException;

import java.util.List;

public interface CRUDOperationService<T> {
    boolean create(T entity)
            throws ServiceException;

    boolean deleteById(long id)
            throws ServiceException;

    boolean update(T entity)
            throws ServiceException;

    List<T> findAll(int page, int size)
            throws ServiceException;

    T findById(long id)
            throws ServiceException;
}