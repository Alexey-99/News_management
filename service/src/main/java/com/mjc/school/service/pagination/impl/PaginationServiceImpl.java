package com.mjc.school.service.pagination.impl;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.service.pagination.PaginationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationServiceImpl<T>
        implements PaginationService<T> {
    private static final int NUMBER_MIN_PAGE = 1;
    private static final int NUMBER_FIRST_ELEMENT = 0;
    private static final int NUMBER_ELEMENTS_RETURN = 5;

    @Override
    public Pagination<T> getPagination(List<T> list, int page, int size) {
        Pagination<T> paginationResult = null;
        if (list != null && !list.isEmpty()) {
            size = validCountElementsReturn(size);
            int maxNumberPage = calcMaxNumberPage(list, size);
            page = validNumberPage(page, maxNumberPage);
            int numberStartElement = validNumberStartElement(page, size);
            paginationResult = Pagination
                    .<T>builder()
                    .entity(getListEntity(list, numberStartElement, size))
                    .numberPage(page)
                    .maxNumberPage(maxNumberPage)
                    .build();
        } else {
            paginationResult = Pagination
                    .<T>builder()
                    .entity(new ArrayList<>())
                    .build();
        }
        return paginationResult;
    }

    private List<T> getListEntity(List<T> list, int numberFirstElement, int countElementsReturn) {
        List<T> listResult = new ArrayList<>();
        for (int i = numberFirstElement; i < list.size() &&
                i < numberFirstElement + countElementsReturn; i++) {
            listResult.add(list.get(i));
        }
        return listResult;
    }

    private int validCountElementsReturn(int countElementsReturn) {
        return countElementsReturn > 0 ?
                countElementsReturn :
                NUMBER_ELEMENTS_RETURN;
    }

    private int calcMaxNumberPage(List<T> list, int countElementsReturn) {
        int maxNumberPage = (int) Math.ceil((double) list.size() / countElementsReturn);
        return maxNumberPage > 0 ?
                maxNumberPage :
                NUMBER_MIN_PAGE;
    }

    private int validNumberPage(int numberPage, int maxNumberPage) {
        return (numberPage >= NUMBER_MIN_PAGE) &&
                (numberPage <= maxNumberPage) ?
                numberPage :
                NUMBER_MIN_PAGE;
    }

    private int validNumberStartElement(int numberPage, int countElementsReturn) {
        int numberStartElement = countElementsReturn * (numberPage - 1);
        return numberStartElement >= 0 ?
                numberStartElement :
                NUMBER_FIRST_ELEMENT;
    }
}