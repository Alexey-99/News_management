package com.mjc.school.service;

import com.mjc.school.validation.dto.Pagination;

import java.util.List;

public interface BaseService<T> extends CRUDOperationService<T> {
    Pagination<T> getPagination(List<T> elementsOnPage,
                                List<T> allElementsList,
                                int page, int size);
}
