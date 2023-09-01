package com.mjc.school.service.news.impl.comparator.impl.created;

import com.mjc.school.entity.News;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;

/**
 * The type Sort news comparator by created date time descending.
 */
public class SortNewsComparatorByCreatedDateTimeDesc
        implements SortNewsComparator {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     *
     * @param news1 the first object to be compared.
     * @param news2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     * @apiNote It is generally the case, but <i>not</i> strictly required that
     * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     */
    @Override
    public int compare(News news1, News news2) {
        return news2.getCreated().compareTo(news1.getCreated());
    }
}