package com.mjc.school.repository;

import java.util.List;

public interface CRUDOperationRepository<T> {
    boolean create(T entity);

    boolean deleteById(long id);

    T update(T entity);

    List<T> findAll(int page, int size);

    List<T> findAll();

    T findById(long id);
}