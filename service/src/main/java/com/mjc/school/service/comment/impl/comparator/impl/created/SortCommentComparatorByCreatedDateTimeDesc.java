package com.mjc.school.service.comment.impl.comparator.impl.created;

import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.validation.dto.CommentDTO;

public class SortCommentComparatorByCreatedDateTimeDesc
        implements SortCommentComparator {
    @Override
    public int compare(CommentDTO comment1, CommentDTO comment2) {
        return comment2.getCreated().compareTo(comment1.getCreated());
    }
}