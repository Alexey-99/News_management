package com.mjc.school.logic.pagination;

import java.util.List;

/**
 * The interface Pagination.
 *
 * @param <T> the type parameter
 */
public interface Pagination<T> {
    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    public List<T> getEntity(List<T> list, long numberElementsReturn, long numberPage);
}