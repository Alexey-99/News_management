package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.pagination.Pagination;

import java.util.List;

/**
 * The interface Author service.
 */
public interface AuthorService extends Pagination<Author> {

    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean create(Author author)
            throws ServiceException, IncorrectParameterException;

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean delete(long id)
            throws ServiceException, IncorrectParameterException;

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean update(Author author)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find all authors list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Author> findAllAuthors() throws ServiceException;

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public Author findById(long id)
            throws ServiceException, IncorrectParameterException;


    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException the service exception
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
}