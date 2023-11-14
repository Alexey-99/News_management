package com.mjc.school.service.sort;

import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.service.comment.impl.sort.CommentSortField;

import java.util.Optional;

public interface SortFieldService {
    Optional<String> getOptionalSortField(String sortField, AuthorSortField[] authorSortFields);
    Optional<String> getOptionalSortField(String sortField, CommentSortField[] authorSortFields);
}