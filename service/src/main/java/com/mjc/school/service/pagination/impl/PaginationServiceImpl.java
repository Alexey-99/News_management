package com.mjc.school.service.pagination.impl;

import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.Pagination;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationServiceImpl<T> implements PaginationService<T> {
    private static final int NUMBER_MIN_PAGE = 1;
    private static final int NUMBER_FIRST_ELEMENT = 0;

    @Override
    public Pagination<T> getPagination(
            List<T> elementsOnPage,
            List<T> allElementsList,
            int page, int size) {
        return Pagination
                .<T>builder()
                .entity(elementsOnPage)
                .size(size)
                .numberPage(page)
                .maxNumberPage(
                        calcMaxNumberPage(
                                allElementsList.size(),
                                size))
                .build();
    }

    @Override
    public int calcMaxNumberPage(long allElementsSize, int sizeOnPage) {
        int maxNumberPage = (int) Math.ceil((double) allElementsSize / sizeOnPage);
        return maxNumberPage > 0 ?
                maxNumberPage :
                NUMBER_MIN_PAGE;
    }

    @Override
    public int calcNumberFirstElement(int numberPage, int size) {
        int numberStartElement = size * (numberPage - 1);
        return numberStartElement >= 0 ?
                numberStartElement :
                NUMBER_FIRST_ELEMENT;
    }
}