package com.mjc.school.service.author.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.service.author.impl.comparator.impl.SortAuthorsWithAmountOfWrittenNewsComparatorImpl;
import com.mjc.school.validation.AuthorValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PARAMETERS;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The type Author service.
 */
@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private AuthorValidator authorValidator;
    @Autowired
    private PaginationService<Author> authorPagination;
    @Autowired
    private PaginationService<AuthorIdWithAmountOfWrittenNews> authorIdWithAmountOfWrittenNewsPagination;

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
            return authorValidator.validate(author) &&
                    authorRepository.create(author);
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
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
            log.log(ERROR, e.getMessage());
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
            log.log(ERROR, e.getMessage());
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
    public List<Author> findAll() throws ServiceException {
        try {
            List<Author> authorsList = authorRepository.findAllAuthors();
            if (!authorsList.isEmpty()) {
                return authorsList;
            } else {
                log.log(WARN, "Not found objects");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
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
            if (authorValidator.validateId(id)) {
                Author author = authorRepository.findById(id);
                if (author != null) {
                    return author;
                } else {
                    log.log(WARN, "Not found object with this ID: " + id);
                    throw new ServiceException(NO_ENTITY_WITH_ID);
                }
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<Author> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException {
        try {
            if (partOfName != null) {
                Pattern p = Pattern.compile(partOfName.toLowerCase());
                List<Author> authorsList = authorRepository.findAllAuthors()
                        .stream()
                        .filter(author ->
                                (p.matcher(
                                        author.getName().toLowerCase()).find())
                                        || (p.matcher(
                                        author.getName().toLowerCase()).lookingAt())
                                        || (author.getName().toLowerCase()
                                        .matches(partOfName.toLowerCase()))
                        ).toList();
                if (!authorsList.isEmpty()) {
                    return authorsList;
                } else {
                    log.log(WARN, "Not found object with this part of name: " + partOfName);
                    throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
                }
            } else {
                log.log(ERROR, "Entered part of author name is null");
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_AUTHOR_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
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
                    authorRepository.findByNewsId(newsId) :
                    null;
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
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
            log.log(ERROR, e.getMessage());
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
            log.log(ERROR, e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    @Override
    public Pagination<Author> getPagination(
            List<Author> list, long numberElementsReturn, long numberPage) {
        return authorPagination.getPagination(list, numberElementsReturn, numberPage);
    }

    /**
     * Gets author id with amount of written news.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the pagination author id with amount of written news
     */
    public Pagination<AuthorIdWithAmountOfWrittenNews> getPaginationAuthorIdWithAmountOfWrittenNews
    (List<AuthorIdWithAmountOfWrittenNews> list, long numberElementsReturn,
     long numberPage) {
        return authorIdWithAmountOfWrittenNewsPagination
                .getPagination(list, numberElementsReturn, numberPage);
    }
}