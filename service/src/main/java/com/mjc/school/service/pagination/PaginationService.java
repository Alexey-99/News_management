package com.mjc.school.service.pagination;

import com.mjc.school.entity.Pagination;

import java.util.List;

/**
 * The interface Pagination.
 *
 * @param <T> the type parameter
 */
public interface PaginationService<T> {
    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    public Pagination<T> getPagination(List<T> list, long numberElementsReturn, long numberPage);
}