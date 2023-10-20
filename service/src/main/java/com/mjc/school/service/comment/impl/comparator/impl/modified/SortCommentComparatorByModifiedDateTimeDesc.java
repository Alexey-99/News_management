package com.mjc.school.service.comment.impl.comparator.impl.modified;

import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.validation.dto.CommentDTO;

public class SortCommentComparatorByModifiedDateTimeDesc implements SortCommentComparator {
    @Override
    public int compare(CommentDTO comment1, CommentDTO comment2) {
        return comment2.getModified().compareTo(comment1.getModified());
    }
}