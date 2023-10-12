package com.mjc.school.service.comment;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.validation.dto.CommentDTO;

import java.util.List;

public interface CommentService
        extends PaginationService<CommentDTO>,
        CRUDOperationService<CommentDTO> {
    List<CommentDTO> findByNewsId(long newsId,int page, int size)
            throws ServiceException, IncorrectParameterException;

    List<CommentDTO> sort(List<CommentDTO> list,
                          SortCommentComparator comparator) throws ServiceException;

    List<CommentDTO> sortByCreatedDateTimeAsc(List<CommentDTO> list)
            throws ServiceException;

    List<CommentDTO> sortByCreatedDateTimeDesc(List<CommentDTO> list)
            throws ServiceException;

    List<CommentDTO> sortByModifiedDateTimeAsc(List<CommentDTO> list)
            throws ServiceException;

    List<CommentDTO> sortByModifiedDateTimeDesc(List<CommentDTO> list)
            throws ServiceException;

    boolean deleteByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;
}