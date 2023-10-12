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
import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.repository.impl.news.NewsRepository;
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

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_AUTHOR_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
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
            throws ServiceException {
        return authorRepository.create(
                authorConverter.fromDTO(authorDTO));
    }

    @Override
    public boolean deleteById(long id)
            throws ServiceException {
            newsRepository.deleteByAuthorId(id);
            authorRepository.deleteById(id);
            return newsRepository.findByAuthorId(id, 1, 5).isEmpty()
                    && authorRepository.findById(id) == null;
    }

    @Override
    public boolean update(AuthorDTO authorDTO)
            throws ServiceException {
        return authorRepository.update(
                authorConverter.fromDTO(authorDTO)) != null;
    }

    @Override
    public List<AuthorDTO> findAll(int page, int size) throws ServiceException {
        List<Author> authorsList = authorRepository.findAll(page, size);
        if (!authorsList.isEmpty()) {
            return authorsList
                    .stream()
                    .map(authorConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public AuthorDTO findById(long id)
            throws ServiceException {
        Author author = authorRepository.findById(id);
        if (author != null) {
            //author.setNews(newsRepository.findByAuthorId(author.getId()));
            return authorConverter.toDTO(author);
        } else {
            log.log(WARN, "Not found object with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException {

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
            return authorsList.stream()
                    .map(author -> authorConverter.toDTO(author))
                    .toList();
        } else {
            log.log(WARN, "Not found object with this part of name: " + partOfName);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
        }
    }

    @Override
    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException {
        try {
            Author author = authorRepository.findByNewsId(newsId);
            if (author != null) {
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
    selectAllAuthorsIdWithAmountOfWrittenNews(int page, int size)
            throws ServiceException {
        List<Author> list = authorRepository.findAll(page, size);
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(author ->
                            new AuthorIdWithAmountOfWrittenNews
                                    .AuthorIdWithAmountOfWrittenNewsBuilder()
                                    .setAuthorId(author.getId())
                                    .setAmountOfWrittenNews(
                                            author.getNews() != null
                                                    ? author.getNews().size()
                                                    : 0)
                                    .build())
                    .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(int page, int size)
            throws ServiceException {
        List<AuthorIdWithAmountOfWrittenNews> authorIdWithAmountOfWrittenNewsList =
                new LinkedList<>(authorRepository.findAll(page, size))
                        .stream()
                        .map(author ->
                                new AuthorIdWithAmountOfWrittenNews
                                        .AuthorIdWithAmountOfWrittenNewsBuilder()
                                        .setAuthorId(author.getId())
                                        .setAmountOfWrittenNews(
                                                author.getNews() != null
                                                        ? author.getNews().size()
                                                        : 0)
                                        .build())
                        .toList();
        if (!authorIdWithAmountOfWrittenNewsList.isEmpty()) {
            authorIdWithAmountOfWrittenNewsList.sort(
                    new SortAuthorsWithAmountOfWrittenNewsComparatorImpl());
            return authorIdWithAmountOfWrittenNewsList.stream()
                    .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public Pagination<AuthorDTO> getPagination(
            List<AuthorDTO> list,
            int size, int page) {
        return new Pagination<>();
    }


    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO>
    getPaginationAuthorIdWithAmountOfWrittenNews(
            List<AuthorIdWithAmountOfWrittenNewsDTO> list,
            int size, int page) {
        return new Pagination
                .PaginationBuilder<AuthorIdWithAmountOfWrittenNewsDTO>()
                .setEntity(list)
                .setSize(size)
                .setNumberPage(page)
                .build();
    }
}