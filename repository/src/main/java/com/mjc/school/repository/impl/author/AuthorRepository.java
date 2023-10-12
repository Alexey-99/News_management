package com.mjc.school.repository.impl.author;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface AuthorRepository extends
        CRUDOperationRepository<Author> {
    Author findByNewsId(long newsId) throws RepositoryException;

    boolean isNotExistsAuthorWithName(String name);
}