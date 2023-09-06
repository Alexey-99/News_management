package com.mjc.school.service.news.impl;

import com.mjc.school.entity.News;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeDesc;
import com.mjc.school.validation.AuthorValidator;
import com.mjc.school.validation.NewsValidator;
import com.mjc.school.validation.TagValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_TITLE;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.INSERT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_CONTENT;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_TITLE;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.UPDATE_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The type News service.
 */
@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private NewsValidator newsValidator;
    @Autowired
    private TagValidator tagValidator;
    @Autowired
    private AuthorValidator authorValidator;
    @Autowired
    private DateHandler dateHandler;
    @Autowired
    private PaginationService<News> newsPagination;

    /**
     * Create news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean create(News news)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validate(news)) {
                news.setCreated(dateHandler.getCurrentDate());
                news.setModified(dateHandler.getCurrentDate());
                return newsRepository.create(news);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(INSERT_ERROR);
        }
    }

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteById(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(newsId)) {
                newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
                commentRepository.deleteByNewsId(newsId);
                newsRepository.deleteById(newsId);
                return newsRepository.findById(newsId) == null;
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateAuthorId(authorId)) {
                for (News news : newsRepository.findAll()
                        .stream()
                        .filter(news -> news.getAuthorId() == authorId).toList()) {
                    newsRepository.deleteAllTagsFromNewsByNewsId(news.getId());
                    commentRepository.deleteByNewsId(news.getId());
                    newsRepository.deleteByAuthorId(authorId);
                }
                return newsRepository.findAll()
                        .stream()
                        .filter(news -> news.getAuthorId() == authorId)
                        .toList()
                        .isEmpty();
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    /**
     * Delete by id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(newsId)) {
                newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
                return findAll()
                        .stream()
                        .filter(news -> !news.getTags()
                                .isEmpty())
                        .toList()
                        .isEmpty();
            } else {
                return findAll()
                        .stream()
                        .filter(news -> !news.getTags()
                                .isEmpty())
                        .toList()
                        .isEmpty();
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean update(News news)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(news.getId()) &&
                    newsValidator.validate(news)) {
                news.setModified(dateHandler.getCurrentDate());
                return newsRepository.update(news);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(UPDATE_ERROR);
        }
    }

    /**
     * Find all news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findAll() throws ServiceException {
        try {
            List<News> newsList = newsRepository.findAll();
            if (!newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setComments(commentRepository.findByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                log.log(WARN, "Not found news");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by id news.
     *
     * @param id the news id
     * @return the news
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public News findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(id)) {
                News news = newsRepository.findById(id);
                if (news != null) {
                    news.setComments(commentRepository
                            .findByNewsId(news.getId()));
                    news.setTags(tagRepository
                            .findByNewsId(news.getId()));
                    return news;
                } else {
                    log.log(WARN, "Not found news with this ID: " + id);
                    throw new ServiceException(NO_ENTITY_WITH_ID);
                }
            } else {
                return null;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<News> findByTagName(String tagName)
            throws ServiceException, IncorrectParameterException {
        try {
            if (tagValidator.validateName(tagName)) {
                List<News> newsList = newsRepository.findByTagName(tagName);
                if (!newsList.isEmpty()) {
                    for (News news : newsList) {
                        news.setComments(commentRepository.findByNewsId(news.getId()));
                        news.setTags(tagRepository.findByNewsId(news.getId()));
                    }
                    return newsList;
                } else {
                    log.log(WARN, "Not found news with entered tag name: " + tagName);
                    throw new ServiceException(NO_NEWS_WITH_TAG_NAME);
                }
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<News> findByTagId(long tagId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (tagValidator.validateId(tagId)) {
                List<News> newsList = newsRepository.findByTagId(tagId);
                if (!newsList.isEmpty()) {
                    for (News news : newsList) {
                        news.setComments(commentRepository.findByNewsId(news.getId()));
                        news.setTags(tagRepository.findByNewsId(news.getId()));
                    }
                    return newsList;
                } else {
                    log.log(WARN, "Not found news with entered tag ID: " + tagId);
                    throw new ServiceException(NO_NEWS_WITH_TAG_ID);
                }
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<News> findByAuthorName(String authorName)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorValidator.validateName(authorName)) {
                List<News> newsList = newsRepository.findByAuthorName(authorName);
                if (!newsList.isEmpty()) {
                    for (News news : newsList) {
                        news.setComments(commentRepository.findByNewsId(news.getId()));
                        news.setTags(tagRepository.findByNewsId(news.getId()));
                    }
                    return newsList;
                } else {
                    log.log(WARN, "Not found news with entered author name: " + authorName);
                    throw new ServiceException(NO_NEWS_WITH_TAG_NAME);
                }
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<News> findByPartOfTitle(String partOfTitle)
            throws ServiceException, IncorrectParameterException {
        try {
            if (partOfTitle != null) {
                String pattern = partOfTitle.toLowerCase();
                Pattern p = Pattern.compile(pattern);
                List<News> newsList = findAll()
                        .stream()
                        .filter(news -> {
                            String newsTitle = news.getTitle().toLowerCase();
                            return (p.matcher(newsTitle).find()) ||
                                    (p.matcher(newsTitle).lookingAt()) ||
                                    (newsTitle.matches(pattern));
                        }).toList();
                if (!newsList.isEmpty()) {
                    for (News news : newsList) {
                        news.setComments(commentRepository.findByNewsId(news.getId()));
                        news.setTags(tagRepository.findByNewsId(news.getId()));
                    }
                    return newsList;
                } else {
                    log.log(WARN, "Not found news with this part of title: " + partOfTitle);
                    throw new ServiceException(NO_ENTITY_WITH_PART_OF_TITLE);
                }
            } else {
                log.log(ERROR, "Entered part of news title is null");
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_NEWS_TITLE);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<News> findByPartOfContent(String partOfContent)
            throws ServiceException, IncorrectParameterException {
        try {
            if (partOfContent != null) {
                String pattern = partOfContent.toLowerCase();
                Pattern p = Pattern.compile(pattern);
                List<News> newsList = findAll()
                        .stream()
                        .filter(news -> {
                            String newsContent = news.getTitle().toLowerCase();
                            return (p.matcher(newsContent).find()) ||
                                    (p.matcher(newsContent).lookingAt()) ||
                                    (newsContent.matches(pattern));
                        }).toList();
                if (!newsList.isEmpty()) {
                    for (News news : newsList) {
                        news.setComments(commentRepository.findByNewsId(news.getId()));
                        news.setTags(tagRepository.findByNewsId(news.getId()));
                    }
                    return newsList;
                } else {
                    log.log(WARN, "Not found news with this part of content: " + partOfContent);
                    throw new ServiceException(NO_ENTITY_WITH_PART_OF_CONTENT);
                }
            } else {
                log.log(ERROR, "Entered part of news content is null");
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_NEWS_CONTENT);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sort(List<News> newsList, SortNewsComparator comparator)
            throws ServiceException {
        List<News> sortedNewsList;
        if (newsList != null) {
            if (comparator != null) {
                sortedNewsList = new LinkedList<>(newsList);
                sortedNewsList.sort(comparator);
            } else {
                log.log(ERROR, "comparator is null");
                throw new ServiceException(SORT_ERROR);
            }
        } else {
            log.log(ERROR, "list is null");
            throw new ServiceException(SORT_ERROR);
        }
        return sortedNewsList;
    }

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortByCreatedDateTimeAsc(List<News> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByCreatedDateTimeAsc());
    }

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortByCreatedDateTimeDesc(List<News> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByCreatedDateTimeDesc());
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortByModifiedDateTimeAsc(List<News> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByModifiedDateTimeAsc());
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortByModifiedDateTimeDesc(List<News> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByModifiedDateTimeDesc());
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
    public Pagination<News> getPagination(List<News> list,
                                          long numberElementsReturn,
                                          long numberPage) {
        return newsPagination.getPagination(list, numberElementsReturn, numberPage);
    }
}