package com.mjc.school.service.pagination;

public interface PaginationService {
    String DEFAULT_SIZE = "5";
    String DEFAULT_NUMBER_PAGE = "1";

    int calcNumberFirstElement(int page, int size);

    int calcMaxNumberPage(long allElementsSize, int sizeOnPage);
}