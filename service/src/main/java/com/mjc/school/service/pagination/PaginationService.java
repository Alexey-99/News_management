package com.mjc.school.service.pagination;

import com.mjc.school.validation.dto.Pagination;

import java.util.List;

public interface PaginationService<T> {
    String DEFAULT_SIZE = "5";
    String DEFAULT_NUMBER_PAGE = "1";

    Pagination<T> getPagination(
            List<T> elementsOnPage,
            List<T> allElementsList,
            int page, int size);

    int calcNumberFirstElement(int page, int size);

    int calcMaxNumberPage(long allElementsSize, int sizeOnPage);
}