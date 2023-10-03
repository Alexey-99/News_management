package com.mjc.school.service.author.impl;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.AuthorIdWithAmountOfWrittenNewsConverter;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.service.author.impl.comparator.impl.SortAuthorsWithAmountOfWrittenNewsComparatorImpl;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;
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
    private AuthorConverter authorConverter;
    @Autowired
    private AuthorIdWithAmountOfWrittenNewsConverter
            authorIdWithAmountOfWrittenNewsConverter;
    @Autowired
    private PaginationService<AuthorDTO> authorPagination;
    @Autowired
    private PaginationService<AuthorIdWithAmountOfWrittenNewsDTO>
            authorIdWithAmountOfWrittenNewsPagination;

    @Override
    public boolean create(AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            if (!authorRepository.isExistsAuthorWithName(
                    authorDTO.getName())) {
                return authorRepository.create(
                        authorConverter.fromDTO(authorDTO));
            } else {
                log.log(WARN, "Author with entered name '" + authorDTO.getName() + "' already exists");
                throw new IncorrectParameterException(BAD_PARAMETER_AUTHOR_NAME_EXISTS);
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
            newsRepository.deleteByAuthorId(id);
            authorRepository.deleteById(id);
            return newsRepository.findByAuthorId(id).isEmpty()
                    && authorRepository.findById(id) == null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean update(AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            return authorRepository.update(
                    authorConverter.fromDTO(authorDTO));
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
            Author author = authorRepository.findById(id);
            if (author != null) {
                author.setNews(newsRepository.findByAuthorId(author.getId()));
                return authorConverter.toDTO(author);
            } else {
                log.log(WARN, "Not found object with this ID: " + id);
                throw new ServiceException(NO_ENTITY_WITH_ID);
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
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            Author author = authorRepository.findByNewsId(newsId);
            if (author != null) {
                author.setNews(newsRepository.findByAuthorId(author.getId()));
                return authorConverter.toDTO(author);
            } else {
                log.log(WARN, "Not found objects with author news ID: " + newsId);
                throw new ServiceException(NO_ENTITY_WITH_AUTHOR_NEWS_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews()
            throws ServiceException {
        try {
            List<AuthorIdWithAmountOfWrittenNews> list =
                    authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews();
            if (!list.isEmpty()) {
                return list.stream()
                        .map(authorIdWithAmountOfWrittenNews ->
                                authorIdWithAmountOfWrittenNewsConverter
                                        .toDTO(authorIdWithAmountOfWrittenNews))
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
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc()
            throws ServiceException {
        try {
            List<AuthorIdWithAmountOfWrittenNews> authorLongMapSorted =
                    new LinkedList<>(
                            authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews());
            if (!authorLongMapSorted.isEmpty()) {
                authorLongMapSorted.sort(
                        new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
                return authorLongMapSorted.stream()
                        .map(authorIdWithAmountOfWrittenNews ->
                                authorIdWithAmountOfWrittenNewsConverter
                                        .toDTO(authorIdWithAmountOfWrittenNews))
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
    public Pagination<AuthorDTO> getPagination(
            List<AuthorDTO> list,
            long size, long page) {
        return authorPagination.getPagination(list, size, page);
    }

    @Override
    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO>
    getPaginationAuthorIdWithAmountOfWrittenNews
            (List<AuthorIdWithAmountOfWrittenNewsDTO> list,
             long size, long page) {
        return authorIdWithAmountOfWrittenNewsPagination
                .getPagination(list, size, page);
    }
}