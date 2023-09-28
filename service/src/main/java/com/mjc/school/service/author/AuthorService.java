package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;

import java.util.List;

/**
 * The interface Author service.
 */
public interface AuthorService
        extends PaginationService<Author>, CRUDOperationService<Author, Author> {
    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<Author> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public Author findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Select all authors id with amount of written news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException;

    /**
     * Sort all authors id with amount of written news desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<AuthorIdWithAmountOfWrittenNews> sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException;

    /**
     * Gets author id with amount of written news.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the pagination author id with amount of written news
     */
    public Pagination<AuthorIdWithAmountOfWrittenNews> getPaginationAuthorIdWithAmountOfWrittenNews
    (List<AuthorIdWithAmountOfWrittenNews> list, long numberElementsReturn, long numberPage);
}