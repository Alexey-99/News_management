package com.mjc.school.service.author;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorService {
    @Transactional
    boolean create(AuthorDTO authorDTO) throws ServiceException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    AuthorDTO update(AuthorDTO authorDTO) throws ServiceException;

    List<AuthorDTO> findAll(int page, int size) throws ServiceException;

    List<AuthorDTO> findAll();

    long countAll();

    AuthorDTO findById(long id) throws ServiceException;

    List<AuthorDTO> findByPartOfName(String partOfName, int page, int size) throws ServiceException;

    List<AuthorDTO> findByPartOfName(String partOfName);

    long countAllByPartOfName(String partOfName);

    AuthorDTO findByNewsId(long newsId) throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews(int page, int size) throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO> selectAllAuthorsIdWithAmountOfWrittenNews();

    List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(int page, int size) throws ServiceException;

    List<AuthorIdWithAmountOfWrittenNewsDTO> sortAllAuthorsIdWithAmountOfWrittenNewsDesc();

    Pagination<AuthorDTO> getPagination(List<AuthorDTO> elementsOnPage, long countAllElements,
                                        int page, int size);

    Pagination<AuthorIdWithAmountOfWrittenNewsDTO> getPaginationAuthorIdWithAmountOfWrittenNews(
            List<AuthorIdWithAmountOfWrittenNewsDTO> elementsOnPage, long countAllElements, int page, int size);
}