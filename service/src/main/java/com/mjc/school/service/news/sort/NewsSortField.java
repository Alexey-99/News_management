package com.mjc.school.service.news.sort;

import java.util.Optional;

public enum NewsSortField {
    CREATED, MODIFIED;

    public static String getSortField(String sortField) {
        Optional<String> currentSortField = Optional.empty();
        if (sortField != null) {
            for (NewsSortField newsSortFieldElement : values()) {
                if (newsSortFieldElement.toString().equalsIgnoreCase(sortField)) {
                    currentSortField = Optional.of(newsSortFieldElement.toString().toLowerCase());
                }
            }
        }
        return currentSortField.orElse(MODIFIED.toString().toLowerCase());
    }
}