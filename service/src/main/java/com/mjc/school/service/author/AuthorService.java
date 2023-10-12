package com.mjc.school.service.author;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;

import java.util.List;

public interface AuthorService
        extends PaginationService<AuthorDTO>,
        CRUDOperationService<AuthorDTO> {
    List<AuthorDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException, IncorrectParameterException;

    AuthorDTO findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;

    List<AuthorIdWithAmountOfWrittenNewsDTO> selectAllAuthorsIdWithAmountOfWrittenNews(int page, int size)
            throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO> sortAllAuthorsIdWithAmountOfWrittenNewsDesc(int page, int size)
            throws ServiceException;

    Pagination<AuthorIdWithAmountOfWrittenNewsDTO> getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNewsDTO> list, int size, int page);
}