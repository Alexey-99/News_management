package com.mjc.school.converter;

public interface Converter<D, T> {
    T fromDTO(D entityDTO);

    D toDTO(T entity);
}