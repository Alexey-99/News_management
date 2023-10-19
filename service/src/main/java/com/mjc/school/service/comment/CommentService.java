package com.mjc.school.service.comment;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;

import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentService {
    @Transactional
    boolean create(CommentDTO commentDTO) throws ServiceException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    CommentDTO update(CommentDTO commentDTO) throws ServiceException;

    @Transactional
    boolean deleteByNewsId(long newsId) throws ServiceException;

    List<CommentDTO> findAll(int page, int size) throws ServiceException;

    List<CommentDTO> findAll();

    CommentDTO findById(long id) throws ServiceException;

    List<CommentDTO> findByNewsId(long newsId,
                                  int page, int size) throws ServiceException;

    List<CommentDTO> findByNewsId(long newsId);

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

    Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage,
                                         List<CommentDTO> allElementsList,
                                         int page, int size);
}