package com.mjc.school.service.pagination.impl;

import com.mjc.school.service.pagination.PaginationService;
import org.springframework.stereotype.Component;

@Component
public class PaginationServiceImpl implements PaginationService {
    @Override
    public int calcNumberFirstElement(int page, int size) {
        return size * (page - 1);
    }

    @Override
    public int calcMaxNumberPage(long allElementsSize, int size) {
        return (int) Math.ceil((double) allElementsSize / size);
    }
}