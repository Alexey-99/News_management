package com.mjc.school.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Pagination<T> implements Serializable {
    private static final int DEFAULT_SIZE = 5;
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
}