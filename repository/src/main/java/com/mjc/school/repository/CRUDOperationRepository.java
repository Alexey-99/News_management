package com.mjc.school.repository;

import com.mjc.school.exception.RepositoryException;

import java.util.List;

public interface CRUDOperationRepository<T> {
    public boolean create(T entity) throws RepositoryException;

    public boolean deleteById(long id) throws RepositoryException;

    public boolean update(T entity) throws RepositoryException;

    public List<T> findAll() throws RepositoryException;

    public T findById(long id) throws RepositoryException;
}