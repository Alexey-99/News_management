package com.mjc.school.converter;

public interface Converter<D, T> {
    public T fromDTO(D entityDTO);

    public D toDTO(T entity);
}