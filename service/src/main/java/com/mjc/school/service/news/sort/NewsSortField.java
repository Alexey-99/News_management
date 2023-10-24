package com.mjc.school.service.news.sort;

import java.util.Optional;

public enum NewsSortField {
    CREATED, MODIFIED;

    public static Optional<String> getSortField(String sortField) {
        try {
            return Optional.of(valueOf(sortField.toUpperCase()).toString().toLowerCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}