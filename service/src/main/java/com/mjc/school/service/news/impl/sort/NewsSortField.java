package com.mjc.school.service.news.impl.sort;

import java.util.Optional;

public enum NewsSortField {
    CREATED, MODIFIED;

    public static Optional<String> getSortField(String sortField) {
        try {
            return sortField != null ?
                    Optional.of(valueOf(sortField.toUpperCase()).name().toLowerCase()) :
                    Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}