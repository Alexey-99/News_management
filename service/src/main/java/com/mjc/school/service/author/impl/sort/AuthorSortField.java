package com.mjc.school.service.author.impl.sort;

import java.util.Optional;

public enum AuthorSortField {
    ID, NAME;

    public static Optional<String> getSortField(String sortField) {
        try {
            return Optional.of(valueOf(sortField.toUpperCase()).toString().toLowerCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}