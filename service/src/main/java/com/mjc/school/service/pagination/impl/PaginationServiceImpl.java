package com.mjc.school.service.pagination.impl;

import com.mjc.school.entity.Pagination;
import com.mjc.school.service.pagination.PaginationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pagination service.
 *
 * @param <T> the type parameter
 */
@Component
public class PaginationServiceImpl<T> implements PaginationService<T> {
    private static final long NUMBER_MIN_PAGE = 1;
    private static final long NUMBER_FIRST_ELEMENT = 0;
    private static final long NUMBER_ELEMENTS_RETURN = 5;

    /**
     * Get objects from list.
     *
     * @param list                the list
     * @param countElementsReturn the count elements return
     * @param numberPage          the number page
     * @return the entity
     */
    @Override
    public Pagination<T> getPagination(List<T> list, long countElementsReturn, long numberPage) {
        Pagination<T> paginationResult = null;
        if (list != null && !list.isEmpty()) {
            countElementsReturn = validCountElementsReturn(countElementsReturn);
            long maxNumberPage = calcMaxNumberPage(list, countElementsReturn);
            numberPage = validNumberPage(numberPage, maxNumberPage);
            long numberStartElement = validNumberStartElement(numberPage, countElementsReturn);
            paginationResult = new Pagination
                    .PaginationBuilder<T>()
                    .setEntity(getListEntity(list, numberStartElement, countElementsReturn))
                    .setNumberPage(numberPage)
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