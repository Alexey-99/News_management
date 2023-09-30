package com.mjc.school.service.pagination.impl;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.service.pagination.PaginationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationServiceImpl<T> implements PaginationService<T> {
    private static final long NUMBER_MIN_PAGE = 1;
    private static final long NUMBER_FIRST_ELEMENT = 0;
    private static final long NUMBER_ELEMENTS_RETURN = 5;

    @Override
    public Pagination<T> getPagination(List<T> list, long size, long page) {
        Pagination<T> paginationResult = null;
        if (list != null && !list.isEmpty()) {
            size = validCountElementsReturn(size);
            long maxNumberPage = calcMaxNumberPage(list, size);
            page = validNumberPage(page, maxNumberPage);
            long numberStartElement = validNumberStartElement(page, size);
            paginationResult = new Pagination
                    .PaginationBuilder<T>()
                    .setEntity(getListEntity(list, numberStartElement, size))
                    .setNumberPage(page)
                    .setMaxNumberPage(maxNumberPage)
                    .build();
        } else {
            paginationResult = new Pagination
                    .PaginationBuilder<T>()
                    .setEntity(new ArrayList<>())
                    .build();
        }
        return paginationResult;
    }

    private List<T> getListEntity(List<T> list, long numberFirstElement, long countElementsReturn) {
        List<T> listResult = new ArrayList<>();
        for (long i = numberFirstElement; i < list.size() &&
                i < numberFirstElement + countElementsReturn; i++) {
            listResult.add(list.get((int) i));
        }
        return listResult;
    }

    private long validCountElementsReturn(long countElementsReturn) {
        return countElementsReturn > 0 ?
                countElementsReturn :
                NUMBER_ELEMENTS_RETURN;
    }

    private long calcMaxNumberPage(List<T> list, long countElementsReturn) {
        long maxNumberPage = (long) Math.ceil((double) list.size() / countElementsReturn);
        return maxNumberPage > 0 ?
                maxNumberPage :
                NUMBER_MIN_PAGE;
    }

    private long validNumberPage(long numberPage, long maxNumberPage) {
        return (numberPage >= NUMBER_MIN_PAGE) &&
                (numberPage <= maxNumberPage) ?
                numberPage :
                NUMBER_MIN_PAGE;
    }

    private long validNumberStartElement(long numberPage, long countElementsReturn) {
        long numberStartElement = countElementsReturn * (numberPage - 1);
        return numberStartElement >= 0 ?
                numberStartElement :
                NUMBER_FIRST_ELEMENT;
    }
}