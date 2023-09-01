package com.mjc.school.service.author;

import com.mjc.school.entity.Author;
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
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    public List<Author> findByPartOfName(String partOfName);

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    public Author findByNewsId(long newsId);

    /**
     * Sort all authors with amount of written news desc map.
     *
     * @param authorLongMap the author long map
     * @return the map
     */
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc(
            List<Entry<Author, Long>> authorLongMap)
            throws SortException;

    /**
     * Select all authors with amount of written news map.
     *
     * @return the map
     */
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews();
}