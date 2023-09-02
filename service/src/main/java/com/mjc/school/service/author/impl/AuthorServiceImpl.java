package com.mjc.school.service.author.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.service.author.impl.comparator.impl.SortAuthorsWithAmountOfWrittenNewsComparatorImpl;
import com.mjc.school.validation.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * The type Author service.
 */
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AuthorValidator authorValidator;

    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean create(Author author)
            throws ServiceException, IncorrectParameterException {
        try {
            return authorValidator.validate(author) && authorRepository.create(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean delete(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateId(id)) {
                newsRepository.deleteByAuthorId(id);
                authorRepository.delete(id);
                return newsRepository.findNewsByAuthorId(id).isEmpty()
                        && authorRepository.findById(id) == null;
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean update(Author author)
            throws ServiceException, IncorrectParameterException {
        try {
            return authorValidator.validateId(author.getId()) &&
                    authorValidator.validate(author) &&
                    authorRepository.update(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find all authors list.
     *
     * @return the list
     * @throws ServiceException the service exception
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
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public Author findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            return authorValidator.validateId(id) ?
                    authorRepository.findById(id) : null;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException the service exception
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
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public Author findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return authorValidator.validateId(newsId) ?
                    authorRepository.findByNewsId(newsId) : null;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Select all authors id with amount of written news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException {
        try {
            return authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sort all authors id with amount of written news desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<AuthorIdWithAmountOfWrittenNews> sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException {
        try {
            List<AuthorIdWithAmountOfWrittenNews> authorLongMapSorted =
                    new LinkedList<>(
                            authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews());
            authorLongMapSorted.sort(
                    new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
            return authorLongMapSorted;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}