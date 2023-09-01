package com.mjc.school.service.comment.impl.comparator.impl.modified;

import com.mjc.school.entity.Comment;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;

/**
 * The type Sort comments comparator by created date time by descending.
 */
public class SortCommentComparatorByModifiedDateTimeDesc
        implements SortCommentComparator {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     *
     * @param comment1 the first object to be compared.
     * @param comment2 the second object to be compared.
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
    public int compare(Comment comment1, Comment comment2) {
        return comment2.getModified().compareTo(comment1.getModified());
    }
}