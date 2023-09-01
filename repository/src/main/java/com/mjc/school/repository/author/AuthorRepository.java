package com.mjc.school.repository.author;

import com.mjc.school.entity.Author;

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
    public boolean create(Author author);

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean delete(long id);

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    public boolean update(Author author);

    /**
     * Find all authors list.
     *
     * @return the list
     */
    public List<Author> findAllAuthors();

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    public Author findById(long id);

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    public Author findByNewsId(long newsId);

    /**
     * Select all authors with amount of written news list.
     *
     * @return the list
     */
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews();
}
