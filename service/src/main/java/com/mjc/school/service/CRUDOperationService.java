package com.mjc.school.service;

import com.mjc.school.exception.ServiceException;

import java.util.List;

public interface CRUDOperationService<DTO> {
    boolean create(DTO entity)
            throws ServiceException;

    boolean deleteById(long id)
            throws ServiceException;

    DTO update(DTO entity)
            throws ServiceException;

    List<DTO> findAll(int page, int size)
            throws ServiceException;

    DTO findById(long id)
            throws ServiceException;
}