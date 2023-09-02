package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;

import java.util.List;
import java.util.Map.Entry;

/**
 * The interface Author service.
 */
public interface AuthorService {

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
    public Author findById(long id) throws ServiceException, IncorrectParameterException;


    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Author> findByPartOfName(String partOfName) throws ServiceException;

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
     * Sort all authors with amount of written news desc map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc()
            throws ServiceException;

    /**
     * Select all authors with amount of written news map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews()
            throws ServiceException;
}