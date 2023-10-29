package com.mjc.school.service.author;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorService {
    @Transactional
    boolean create(AuthorDTO authorDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    AuthorDTO update(AuthorDTO authorDTO) throws ServiceBadRequestParameterException;

    List<AuthorDTO> findAll(int page, int size, String sortField, String sortingType) throws ServiceNoContentException;

    List<AuthorDTO> findAll();

    long countAll();

    AuthorDTO findById(long id) throws ServiceNoContentException;

    List<AuthorDTO> findByPartOfName(String partOfName, int page, int size, String sortField, String sortingType) throws ServiceNoContentException;

    long countAllByPartOfName(String partOfName);

    AuthorDTO findByNewsId(long newsId) throws ServiceNoContentException;

    List<AuthorIdWithAmountOfWrittenNewsDTO> findAllAuthorsIdWithAmountOfWrittenNews(int page, int size,
                                                                                     String sortingType) throws ServiceNoContentException;

    Pagination<AuthorDTO> getPagination(List<AuthorDTO> elementsOnPage, long countAllElements, int page, int size);

    Pagination<AuthorIdWithAmountOfWrittenNewsDTO> getPaginationAuthorIdWithAmountOfWrittenNews(
            List<AuthorIdWithAmountOfWrittenNewsDTO> elementsOnPage, long countAllElements, int page, int size);
}