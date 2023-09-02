package com.mjc.school.service.author.impl.comparator.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.service.author.impl.comparator.SortAuthorsWithAmountOfWrittenNewsComparator;

import java.util.Map.Entry;

public class SortAuthorsWithAmountOfWrittenNewsComparatorImpl
        implements SortAuthorsWithAmountOfWrittenNewsComparator {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * <p>
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
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
    public int compare(AuthorIdWithAmountOfWrittenNews o1, AuthorIdWithAmountOfWrittenNews o2) {
        return (int) (o2.getAmountOfWrittenNews() - o1.getAmountOfWrittenNews());
    }
}