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
    public List<AuthorDTO> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException;

    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;

    public List<AuthorIdWithAmountOfWrittenNewsDTO> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException;

    public List<AuthorIdWithAmountOfWrittenNewsDTO> sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException;

    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO> getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNewsDTO> list, long numberElementsReturn, long numberPage);
}