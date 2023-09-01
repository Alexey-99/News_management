package com.mjc.school.service.author.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.SortException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Author service.
 */
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;

    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     */
    @Override
    public boolean create(Author author) {
        return authorRepository.create(author);
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public boolean delete(long id) {
        return newsRepository.deleteByAuthorId(id)
                && authorRepository.delete(id);
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    @Override
    public boolean update(Author author) {
        return authorRepository.update(author);
    }

    /**
     * Find all authors list.
     *
     * @return the list
     */
    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAllAuthors();
    }

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    @Override
    public Author findById(long id) {
        return authorRepository.findById(id);
    }

    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    @Override
    public List<Author> findByPartOfName(String partOfName) {
        Pattern p = Pattern.compile(partOfName);
        return findAllAuthors()
                .stream()
                .filter(author ->
                        (p.matcher(author.getName()).find())
                                || (p.matcher(author.getName()).lookingAt())
                                || (author.getName().matches(partOfName))
                ).collect(Collectors.toList());
    }


    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    @Override
    public Author findByNewsId(long newsId) {
        return authorRepository.findByNewsId(newsId);
    }

    /**
     * Select all authors with amount of written news map.
     *
     * @return the map
     */
    @Override
    public List<Map.Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews() {
        return authorRepository.selectAllAuthorsWithAmountOfWrittenNews();
    }

    /**
     * Sort all authors with amount of written news desc map.
     *
     * @param authorLongMap the author long map
     * @return the map
     */
    @Override
    public List<Map.Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc(
            List<Map.Entry<Author, Long>> authorLongMap)
            throws SortException {
        List<Map.Entry<Author, Long>> authorLongMapSorted;
        if (authorLongMap != null) {
            authorLongMapSorted = new LinkedList<>(authorLongMap);
            authorLongMapSorted.sort(
                    new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
        } else {
            throw new SortException("list is null");
        }
        return authorLongMapSorted;
    }
}