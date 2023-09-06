package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pagination.
 *
 * @param <T> the type parameter
 */
public class Pagination<T> extends AbstractEntity {
    private List<T> entity;
    private long numberPage;
    private long maxNumberPage;

    /**
     * Instantiates a new Pagination.
     */
    public Pagination() {
        this.entity = new ArrayList<>();
        this.maxNumberPage = 1;
        this.numberPage = 1;
    }

    /**
     * Gets entity.
     *
     * @return the list
     */
    public List<T> getEntity() {
        return entity;
    }

    /**
     * Sets list.
     *
     * @param entity the list
     */
    public void setEntity(List<T> entity) {
        this.entity = entity;
    }

    /**
     * Gets number page.
     *
     * @return the number page
     */
    public long getNumberPage() {
        return numberPage;
    }

    /**
     * Sets number page.
     *
     * @param numberPage the number page
     */
    public void setNumberPage(long numberPage) {
        this.numberPage = numberPage;
    }

    /**
     * Gets max number page.
     *
     * @return the max number page
     */
    public long getMaxNumberPage() {
        return maxNumberPage;
    }

    /**
     * Sets max number page.
     *
     * @param maxNumberPage the max number page
     */
    public void setMaxNumberPage(long maxNumberPage) {
        this.maxNumberPage = maxNumberPage;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + (this.entity != null ? this.entity.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.numberPage);
        result = result * PRIME + Long.hashCode(this.maxNumberPage);
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Pagination<T> otherPagination = (Pagination<T>) object;
        if (this.entity == null) {
            if (otherPagination.entity != null) {
                return false;
            }
        } else if (!this.entity.equals(otherPagination.entity)) {
            return false;
        }
        if (this.numberPage != otherPagination.numberPage) {
            return false;
        }
        if (this.maxNumberPage != otherPagination.maxNumberPage) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Pagination{");
        builder.append("list=").append(entity);
        builder.append(", numberPage=").append(numberPage);
        builder.append(", maxNumberPage=").append(maxNumberPage);
        builder.append('}');
        return builder.toString();
    }

    /**
     * The type Pagination builder.
     *
     * @param <T> the type parameter
     */
    public static class PaginationBuilder<T> {
        private Pagination<T> pagination;

        /**
         * Instantiates a new Pagination builder.
         */
        public PaginationBuilder() {
            this.pagination = new Pagination<>();
        }

        /**
         * Sets list.
         *
         * @param list the list
         * @return the list
         */
        public PaginationBuilder<T> setEntity(List<T> list) {
            this.pagination.setEntity(list);
            return this;
        }

        /**
         * Sets number page.
         *
         * @param numberPage the number page
         * @return the number page
         */
        public PaginationBuilder<T> setNumberPage(long numberPage) {
            this.pagination.setNumberPage(numberPage);
            return this;
        }

        /**
         * Sets max number page.
         *
         * @param maxNumberPage the max number page
         * @return the max number page
         */
        public PaginationBuilder<T> setMaxNumberPage(long maxNumberPage) {
            this.pagination.setMaxNumberPage(maxNumberPage);
            return this;
        }

        /**
         * Build pagination.
         *
         * @return the pagination
         */
        public Pagination<T> build() {
            return pagination;
        }
    }
}