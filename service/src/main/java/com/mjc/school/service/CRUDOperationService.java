package com.mjc.school.service;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;

import java.util.List;

public interface CRUDOperationService<T> {
    public boolean create(T entity)
            throws IncorrectParameterException, ServiceException;

    public boolean deleteById(long id)
            throws ServiceException, IncorrectParameterException;

    public boolean update(T entity)
            throws ServiceException, IncorrectParameterException;

    public List<T> findAll()
            throws ServiceException;

    public T findById(long id)
            throws ServiceException, IncorrectParameterException;
}