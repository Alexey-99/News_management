package com.mjc.school.service.pagination;

import com.mjc.school.entity.Pagination;

import java.util.List;

public interface PaginationService<T> {
    String DEFAULT_SIZE = "5";
    String DEFAULT_NUMBER_PAGE = "1";
    public Pagination<T> getPagination(List<T> list, long numberElementsReturn, long numberPage);
}