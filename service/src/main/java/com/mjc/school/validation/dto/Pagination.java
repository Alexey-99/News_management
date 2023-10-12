package com.mjc.school.validation.dto;

import com.mjc.school.validation.dto.abstraction.AbstractEntityDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T>
        extends AbstractEntityDTO
        implements Serializable {
    private static final int DEFAULT_SIZE = 1;
    private static final int DEFAULT_NUMBER_PAGE = 1;
    private List<T> entity;

    private int size;
    private int numberPage;
    private int maxNumberPage;

    public Pagination() {
        this.entity = new ArrayList<>();
        this.maxNumberPage = DEFAULT_NUMBER_PAGE;
        this.numberPage = DEFAULT_NUMBER_PAGE;
        this.size = DEFAULT_SIZE;
    }

    public List<T> getEntity() {
        return entity;
    }


    public void setEntity(List<T> entity) {
        this.entity = entity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getMaxNumberPage() {
        return maxNumberPage;
    }

    public void setMaxNumberPage(int maxNumberPage) {
        this.maxNumberPage = maxNumberPage;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + (this.entity != null ? this.entity.hashCode() : 1);
        result = result * PRIME + this.size;
        result = result * PRIME + this.numberPage;
        result = result * PRIME + this.maxNumberPage;
        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
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
        if (this.size != otherPagination.size) {
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
        final StringBuilder sb = new StringBuilder("Pagination{");
        sb.append("entity=").append(entity);
        sb.append(", size=").append(size);
        sb.append(", numberPage=").append(numberPage);
        sb.append(", maxNumberPage=").append(maxNumberPage);
        sb.append('}');
        return sb.toString();
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

        public PaginationBuilder<T> setSize(int size) {
            this.pagination.setSize(size);
            return this;
        }

        public PaginationBuilder<T> setNumberPage(int numberPage) {
            this.pagination.setNumberPage(numberPage);
            return this;
        }

        public PaginationBuilder<T> setMaxNumberPage(int maxNumberPage) {
            this.pagination.setMaxNumberPage(maxNumberPage);
            return this;
        }

        public Pagination<T> build() {
            return pagination;
        }
    }
}