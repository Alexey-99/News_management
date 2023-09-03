package com.mjc.school.logic.pagination.impl;

import com.mjc.school.logic.pagination.Pagination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pagination.
 *
 * @param <T> the type parameter
 */
@Component
public class PaginationImpl<T> implements Pagination<T> {
    private static final long NUMBER_MIN_PAGE = 1;
    private static final long NUMBER_FIRST_ELEMENT = 0;

    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    @Override
    public List<T> getEntity(List<T> list, long numberElementsReturn, long numberPage) {
        List<T> listResult = null;
        if (list != null) {
            if (!list.isEmpty()) {
                long maxNumberPage = (long) Math.ceil((double) list.size() / numberElementsReturn);
                if ((numberPage >= NUMBER_MIN_PAGE) && (numberPage <= maxNumberPage)) {
                    long numberStartElement = numberElementsReturn * (numberPage - 1);
                    listResult = getListEntity(list, numberStartElement, numberElementsReturn);

                } else {
                    listResult = getListEntity(list, NUMBER_FIRST_ELEMENT, numberElementsReturn);
                }
            } else {
                listResult = new ArrayList<>();
            }
        } else {
            listResult = new ArrayList<>();
        }
        return listResult;
    }

    private List<T> getListEntity(List<T> list, long numberFirstElement, long numberElementsReturn) {
        List<T> listResult = new ArrayList<>();
        for (long i = numberFirstElement; i < list.size() &&
                i < numberFirstElement + numberElementsReturn; i++) {
            listResult.add(list.get((int) i));
        }
        return listResult;
    }
}