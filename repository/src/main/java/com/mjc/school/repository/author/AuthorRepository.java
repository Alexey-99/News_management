package com.mjc.school.repository.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface AuthorRepository extends CRUDOperationRepository<Author> {
    public Author findByNewsId(long newsId) throws RepositoryException;

    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws RepositoryException;

    public boolean isExistsAuthorWithName(String name) throws RepositoryException;
}
