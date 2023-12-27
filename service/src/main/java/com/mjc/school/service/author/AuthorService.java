package com.mjc.school.service.author;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    Pagination<AuthorDTO> getPagination(List<AuthorDTO> elementsOnPage, long countAllElements, int page, int size);

    Optional<AuthorSortField> getOptionalSortField(String sortField);
}