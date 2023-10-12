package com.mjc.school.converter;

import com.mjc.school.exception.RepositoryException;

public interface Converter<D, T> {
    T fromDTO(D entityDTO) throws RepositoryException;

    D toDTO(T entity);
}