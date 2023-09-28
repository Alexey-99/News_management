package com.mjc.school.service.comment.impl.comparator;

import com.mjc.school.validation.dto.CommentDTO;

import java.util.Comparator;

public interface SortCommentComparator extends Comparator<CommentDTO> {
    @Override
    public int compare(CommentDTO commentDTO1, CommentDTO commentDTO2);
}