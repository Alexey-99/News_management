package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.entity.abstation.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> extends AbstractEntity {
    private List<T> entity;
    private long numberPage;
    private long maxNumberPage;

    public Pagination() {
        this.entity = new ArrayList<>();
        this.maxNumberPage = 1;
        this.numberPage = 1;
    }

    public List<T> getEntity() {
        return entity;
    }

    @JsonIgnore
    public void setEntity(List<T> entity) {
        this.entity = entity;
    }

    public long getNumberPage() {
        return numberPage;
    }

    @JsonIgnore
    public void setNumberPage(long numberPage) {
        this.numberPage = numberPage;
    }

    public long getMaxNumberPage() {
        return maxNumberPage;
    }

    @JsonIgnore
    public void setMaxNumberPage(long maxNumberPage) {
        this.maxNumberPage = maxNumberPage;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + (this.entity != null ? this.entity.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.numberPage);
        result = result * PRIME + Long.hashCode(this.maxNumberPage);
        return result;
    }

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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Pagination{");
        builder.append("list=").append(entity);
        builder.append(", numberPage=").append(numberPage);
        builder.append(", maxNumberPage=").append(maxNumberPage);
        builder.append('}');
        return builder.toString();
    }

    public static class PaginationBuilder<T> {
        private final Pagination<T> pagination;

        public PaginationBuilder() {
            this.pagination = new Pagination<>();
        }

        public PaginationBuilder<T> setEntity(List<T> list) {
            this.pagination.setEntity(list);
            return this;
        }

        public PaginationBuilder<T> setNumberPage(long numberPage) {
            this.pagination.setNumberPage(numberPage);
            return this;
        }

        public PaginationBuilder<T> setMaxNumberPage(long maxNumberPage) {
            this.pagination.setMaxNumberPage(maxNumberPage);
            return this;
        }

        public Pagination<T> build() {
            return pagination;
        }
    }
}