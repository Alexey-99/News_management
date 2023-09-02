package com.mjc.school.service.author.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.exception.SortException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.service.author.impl.comparator.impl.SortAuthorsWithAmountOfWrittenNewsComparatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
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
    public boolean create(Author author) throws ServiceException {
        try {
            return authorRepository.create(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return newsRepository.deleteByAuthorId(id)
                    && authorRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    @Override
    public boolean update(Author author) throws ServiceException {
        try {
            return authorRepository.update(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find all authors list.
     *
     * @return the list
     */
    @Override
    public List<Author> findAllAuthors() throws ServiceException {
        try {
            return authorRepository.findAllAuthors();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    @Override
    public Author findById(long id) throws ServiceException {
        try {
            return authorRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    @Override
    public List<Author> findByPartOfName(String partOfName) throws ServiceException {
        try {
            Pattern p = Pattern.compile(partOfName.toLowerCase());
            return authorRepository.findAllAuthors()
                    .stream()
                    .filter(author ->
                            (p.matcher(author.getName().toLowerCase()).find())
                                    || (p.matcher(author.getName().toLowerCase()).lookingAt())
                                    || (author.getName().toLowerCase().matches(partOfName.toLowerCase()))
                    ).toList();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    @Override
    public Author findByNewsId(long newsId) throws ServiceException {
        try {
            return authorRepository.findByNewsId(newsId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select all authors with amount of written news map.
     *
     * @return the map
     */
    @Override
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews()
            throws ServiceException {
        try {
            return authorRepository.selectAllAuthorsWithAmountOfWrittenNews();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sort all authors with amount of written news desc map.
     *
     * @return the map
     */
    @Override
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc()
            throws ServiceException {
        try {
            List<Entry<Author, Long>> authorLongMapSorted =
                    new LinkedList<>(authorRepository.selectAllAuthorsWithAmountOfWrittenNews());
            authorLongMapSorted.sort(
                    new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
            return authorLongMapSorted;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}