package com.mjc.school.service.author.impl.sort;

import java.util.Optional;

public enum AuthorSortField {
    ID, NAME;

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