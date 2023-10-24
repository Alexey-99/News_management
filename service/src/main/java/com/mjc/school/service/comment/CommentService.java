package com.mjc.school.service.comment;

import com.mjc.school.exception.ServiceException;

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

    List<CommentDTO> findAll(int page, int size, String sortingField, String sortingType) throws ServiceException;

    List<CommentDTO> findAll();

    long countAllComments();

    CommentDTO findById(long id) throws ServiceException;

    List<CommentDTO> findByNewsId(long newsId, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllCommentsByNewsId(long newsId);

    Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage, long countAllElements, int page, int size);
}