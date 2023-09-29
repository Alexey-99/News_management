package com.mjc.school.service.author.impl;

import com.mjc.school.converter.impl.AuthorConverter;
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
import com.mjc.school.validation.dto.AuthorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.INSERT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_AUTHOR_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.UPDATE_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

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
    private AuthorConverter authorConverter;
    @Autowired
    private PaginationService<AuthorDTO> authorPagination;
    @Autowired
    private PaginationService<AuthorIdWithAmountOfWrittenNews>
            authorIdWithAmountOfWrittenNewsPagination;

    @Override
    public boolean create(AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validate(authorDTO)) {
                return authorRepository.create(
                        authorConverter.fromDTO(authorDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(INSERT_ERROR);
        }
    }

    @Override
    public boolean deleteById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateId(id)) {
                newsRepository.deleteByAuthorId(id);
                authorRepository.deleteById(id);
                return newsRepository.findByAuthorId(id).isEmpty()
                        && authorRepository.findById(id) == null;
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean update(AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateId(authorDTO.getId()) &&
                    authorValidator.validate(authorDTO)) {
                return authorRepository.update(
                        authorConverter.fromDTO(authorDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(UPDATE_ERROR);
        }
    }

    @Override
    public List<AuthorDTO> findAll() throws ServiceException {
        try {
            List<Author> authorsList = authorRepository.findAll();
            if (!authorsList.isEmpty()) {
                for (Author author : authorsList) {
                    author.setNews(newsRepository.findByAuthorId(author.getId()));
                }
                return authorsList
                        .stream()
                        .map(author -> authorConverter.toDTO(author))
                        .toList();
            } else {
                log.log(WARN, "Not found objects");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public AuthorDTO findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateId(id)) {
                Author author = authorRepository.findById(id);
                if (author != null) {
                    author.setNews(newsRepository.findByAuthorId(author.getId()));
                    return authorConverter.toDTO(author);
                } else {
                    log.log(WARN, "Not found object with this ID: " + id);
                    throw new ServiceException(NO_ENTITY_WITH_ID);
                }
            } else {
                throw new IncorrectParameterException(BAD_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException {
        try {
            if (partOfName != null) {
                String pattern = partOfName.toLowerCase();
                Pattern p = Pattern.compile(pattern);
                List<Author> authorsList = authorRepository.findAll()
                        .stream()
                        .filter(author -> {
                            String authorName = author.getName().toLowerCase();
                            return (p.matcher(authorName).find())
                                    || (p.matcher(authorName).lookingAt())
                                    || (authorName.matches(pattern));
                        }).toList();
                if (!authorsList.isEmpty()) {
                    for (Author author : authorsList) {
                        author.setNews(newsRepository.findByAuthorId(author.getId()));
                    }
                    return authorsList.stream()
                            .map(author -> authorConverter.toDTO(author))
                            .toList();
                } else {
                    log.log(WARN, "Not found object with this part of name: " + partOfName);
                    throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
                }
            } else {
                log.log(ERROR, "Entered part of author name is null");
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_AUTHOR_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateId(newsId)) {
                Author author = authorRepository.findByNewsId(newsId);
                if (author != null) {
                    author.setNews(newsRepository.findByAuthorId(author.getId()));
                    return authorConverter.toDTO(author);
                } else {
                    log.log(WARN, "Not found objects with author news ID: " + newsId);
                    throw new ServiceException(NO_ENTITY_WITH_AUTHOR_NEWS_ID);
                }
            } else {
                log.log(ERROR, "Incorrect entered ID:" + newsId);
                throw new IncorrectParameterException(BAD_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNews>
    selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException {
        try {
            List<AuthorIdWithAmountOfWrittenNews> list =
                    authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews();
            if (!list.isEmpty()) {
                return list;
            } else {
                log.log(WARN, "Not found objects");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNews>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException {
        try {
            List<AuthorIdWithAmountOfWrittenNews> authorLongMapSorted =
                    new LinkedList<>(
                            authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews());
            if (!authorLongMapSorted.isEmpty()) {
                authorLongMapSorted.sort(
                        new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
                return authorLongMapSorted;
            } else {
                log.log(WARN, "Not found objects");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public Pagination<AuthorDTO> getPagination(
            List<AuthorDTO> list, long numberElementsReturn, long numberPage) {
        return authorPagination.getPagination(list, numberElementsReturn, numberPage);
    }

    @Override
    public Pagination<AuthorIdWithAmountOfWrittenNews>
    getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNews> list, long numberElementsReturn,
             long numberPage) {
        return authorIdWithAmountOfWrittenNewsPagination
                .getPagination(list, numberElementsReturn, numberPage);
    }
}