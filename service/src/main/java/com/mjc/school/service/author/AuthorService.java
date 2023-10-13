package com.mjc.school.service.author;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;

import java.util.List;

public interface AuthorService extends CRUDOperationService<AuthorDTO> {
    List<AuthorDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException;

    List<AuthorDTO> findByPartOfName(String partOfName);

    AuthorDTO findByNewsId(long newsId)
            throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews(int page, int size)
            throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews();

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(int page, int size)
            throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc();

    Pagination<AuthorDTO> getPagination(List<AuthorDTO> elementsOnPage,
                                        List<AuthorDTO> allElementsList,
                                        int page, int size);

    Pagination<AuthorIdWithAmountOfWrittenNewsDTO>
    getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNewsDTO> elementsOnPage,
             List<AuthorIdWithAmountOfWrittenNewsDTO> allElementsList,
             int page, int size);
}