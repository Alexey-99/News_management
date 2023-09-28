package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;

import java.util.List;

public interface AuthorService
        extends PaginationService<AuthorDTO>,
        CRUDOperationService<AuthorDTO> {
    public List<AuthorDTO> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException;

    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;

    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException;

    public List<AuthorIdWithAmountOfWrittenNews> sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException;

    public Pagination<AuthorIdWithAmountOfWrittenNews> getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNews> list, long numberElementsReturn, long numberPage);
}