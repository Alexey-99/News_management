package com.mjc.school.converter;

import com.mjc.school.exception.ServiceBadRequestParameterException;

public interface Converter<D, T> {
    T fromDTO(D entityDTO) throws ServiceBadRequestParameterException;

    D toDTO(T entity);
}