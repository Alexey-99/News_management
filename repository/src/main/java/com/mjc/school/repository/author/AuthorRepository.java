package com.mjc.school.repository.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

/**
 * The interface Author repository.
 */
public interface AuthorRepository extends CRUDOperationRepository<Author> {
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
