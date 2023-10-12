package com.mjc.school.repository.impl.author;

import com.mjc.school.entity.Author;

import java.util.List;

public interface AuthorRepository
        /*extends CrudRepository<Author, Long>*/ {
    Author findByNewsId(long newsId);

    boolean isNotExistsAuthorWithName(String name);

    boolean create(Author entity);

    boolean deleteById(long id);

    Author update(Author entity);

    List<Author> findAll(int page, int size);

    List<Author> findAll();

    Author findById(long id);
}