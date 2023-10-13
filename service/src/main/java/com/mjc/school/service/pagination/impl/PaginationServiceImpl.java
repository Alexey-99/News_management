package com.mjc.school.service.pagination.impl;

import com.mjc.school.service.pagination.PaginationService;
import org.springframework.stereotype.Component;

@Component
public class PaginationServiceImpl implements PaginationService {
    private static final int NUMBER_MIN_PAGE = 1;
    private static final int NUMBER_FIRST_ELEMENT = 0;

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