package com.mjc.school.service.comment;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentService {
    @Transactional
    CommentDTO create(CommentDTO commentDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    CommentDTO update(CommentDTO commentDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteByNewsId(long newsId);

    List<CommentDTO> findAll(int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    List<CommentDTO> findAll();

    long countAllComments();

    CommentDTO findById(long id) throws ServiceNoContentException;

    List<CommentDTO> findByNewsId(long newsId, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllCommentsByNewsId(long newsId);

    Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage, long countAllElements, int page, int size);

}