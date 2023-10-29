package com.mjc.school.service.comment;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNotFoundException;

import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentService {
    @Transactional
    boolean create(CommentDTO commentDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    CommentDTO update(CommentDTO commentDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteByNewsId(long newsId) throws ServiceNotFoundException;

    List<CommentDTO> findAll(int page, int size, String sortingField, String sortingType) throws ServiceNotFoundException;

    List<CommentDTO> findAll();

    long countAllComments();

    CommentDTO findById(long id) throws ServiceNotFoundException;

    List<CommentDTO> findByNewsId(long newsId, int page, int size, String sortingField, String sortingType) throws ServiceNotFoundException;

    long countAllCommentsByNewsId(long newsId);

    Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage, long countAllElements, int page, int size);
}