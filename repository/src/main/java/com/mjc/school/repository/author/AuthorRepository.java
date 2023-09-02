package com.mjc.school.repository.author;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.RepositoryException;

import java.util.List;
import java.util.Map.Entry;

/**
 * The interface Author repository.
 */
public interface AuthorRepository {
    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     */
    public boolean create(Author author) throws RepositoryException;

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean delete(long id) throws RepositoryException;

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    public boolean update(Author author) throws RepositoryException;

    /**
     * Find all authors list.
     *
     * @return the list
     */
    public List<Author> findAllAuthors() throws RepositoryException;

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    public Author findById(long id) throws RepositoryException;

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    public Author findByNewsId(long newsId) throws RepositoryException;

    /**
     * Select all authors with amount of written news list.
     *
     * @return the list
     */
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews() throws RepositoryException;
}
