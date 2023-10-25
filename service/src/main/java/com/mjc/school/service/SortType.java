package com.mjc.school.service;

import java.util.Optional;

public enum SortType {
    ASC, DESC;

    public static Optional<String> getSortType(String sortType) {
        try {
            return sortType != null ?
                    Optional.of(valueOf(sortType.toUpperCase()).toString()) :
                    Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}