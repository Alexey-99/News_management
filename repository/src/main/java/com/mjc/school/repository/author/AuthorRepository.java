package com.mjc.school.repository.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
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
     * @throws RepositoryException the repository exception
     */
    public boolean create(Author author) throws RepositoryException;

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean delete(long id) throws RepositoryException;

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean update(Author author) throws RepositoryException;

    /**
     * Find all authors list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Author> findAllAuthors() throws RepositoryException;

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     * @throws RepositoryException the repository exception
     */
    public Author findById(long id) throws RepositoryException;

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     * @throws RepositoryException the repository exception
     */
    public Author findByNewsId(long newsId) throws RepositoryException;


    /**
     * Select all authors id with amount of written news list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws RepositoryException;

    /**
     * Is exists author with name.
     *
     * @param name the name
     * @return true - if exists author with name, false - if not exists
     * @throws RepositoryException the repository exception
     */
    public boolean isExistsAuthorWithName(String name) throws RepositoryException;
}
