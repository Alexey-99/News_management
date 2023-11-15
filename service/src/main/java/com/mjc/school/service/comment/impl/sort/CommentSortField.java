package com.mjc.school.service.comment.impl.sort;

import java.util.Optional;

public enum CommentSortField {
    CREATED, MODIFIED;

//    public static Optional<String> getSortField(String sortField) {
//        try {
//            return sortField != null ?
//                    Optional.of(valueOf(sortField.toUpperCase()).name().toLowerCase()) :
//                    Optional.empty();
//        } catch (IllegalArgumentException e) {
//            return Optional.empty();
//        }
//    }
}