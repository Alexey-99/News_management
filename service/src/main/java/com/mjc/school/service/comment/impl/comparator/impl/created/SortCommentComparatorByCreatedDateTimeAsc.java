package com.mjc.school.service.comment.impl.comparator.impl.created;

import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.validation.dto.CommentDTO;

public class SortCommentComparatorByCreatedDateTimeAsc
        implements SortCommentComparator {
    @Override
    public int compare(CommentDTO comment1, CommentDTO comment2) {
        return comment1.getCreated().compareTo(comment2.getCreated());
    }
}