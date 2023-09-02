package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.exception.SortException;

import java.util.List;
import java.util.Map.Entry;

public interface AuthorService {
    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     */
    public boolean create(Author author) throws ServiceException;

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean delete(long id) throws ServiceException;

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    public boolean update(Author author) throws ServiceException;

    /**
     * Find all authors list.
     *
     * @return the list
     */
    public List<Author> findAllAuthors() throws ServiceException;

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    public Author findById(long id) throws ServiceException;


    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    public List<Author> findByPartOfName(String partOfName) throws ServiceException;

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    public Author findByNewsId(long newsId) throws ServiceException;

    /**
     * Sort all authors with amount of written news desc map.
     *
     * @return the map
     */
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc()
            throws ServiceException;

    /**
     * Select all authors with amount of written news map.
     *
     * @return the map
     */
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews() throws ServiceException;
}