package com.mjc.school.service.news.impl;

import com.mjc.school.entity.News;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_CONTENT;
import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_TITLE;

/**
 * The type News service.
 */
@Service
public class NewsServiceImpl implements NewsService {
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
            throw new ServiceException(e);
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
                newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
                commentRepository.deleteByNewsId(newsId);
                newsRepository.deleteById(newsId);
                return newsRepository.findNewsById(newsId) == null;
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                for (News news : newsRepository.findAllNews()
                        .stream()
                        .filter(news -> news.getAuthorId() == authorId).toList()) {
                    newsRepository.deleteByNewsIdFromTableNewsTags(news.getId());
                    commentRepository.deleteByNewsId(news.getId());
                    newsRepository.deleteByAuthorId(authorId);
                }
                return newsRepository.findAllNews()
                        .stream()
                        .filter(news -> news.getAuthorId() == authorId)
                        .toList()
                        .isEmpty();
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
    public boolean deleteByIdFromTableNewsTags(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(newsId)) {
                newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
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
            throw new ServiceException(e);
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
            throw new ServiceException(e);
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
            List<News> newsList = newsRepository.findAllNews();
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public News findById(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (newsValidator.validateId(newsId)) {
                News news = newsRepository.findNewsById(newsId);
                if (news != null) {
                    news.setComments(commentRepository
                            .findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository
                            .findByNewsId(news.getId()));
                }
                return news;
            } else {
                return null;
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                List<News> newsList = newsRepository.findNewsByTagName(tagName);
                for (News news : newsList) {
                    news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                List<News> newsList = newsRepository.findNewsByTagId(tagId);
                for (News news : newsList) {
                    news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                List<News> newsList = newsRepository.findNewsByAuthorName(authorName);
                for (News news : newsList) {
                    news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                Pattern p = Pattern.compile(partOfTitle.toLowerCase());
                List<News> newsList = findAll()
                        .stream()
                        .filter(news ->
                                (p.matcher(news.getTitle().toLowerCase()).find()) ||
                                        (p.matcher(news.getTitle().toLowerCase()).lookingAt()) ||
                                        (news.getTitle().toLowerCase().matches(partOfTitle.toLowerCase()))
                        ).toList();
                for (News news : newsList) {
                    news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_NEWS_TITLE);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                Pattern p = Pattern.compile(partOfContent.toLowerCase());
                List<News> newsList = findAll()
                        .stream()
                        .filter(news ->
                                (p.matcher(news.getContent().toLowerCase()).find()) ||
                                        (p.matcher(news.getContent().toLowerCase()).lookingAt()) ||
                                        (news.getContent().toLowerCase().matches(partOfContent.toLowerCase()))
                        ).toList();
                for (News news : newsList) {
                    news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList;
            } else {
                throw new IncorrectParameterException(BAD_PARAMETER_PART_OF_NEWS_CONTENT);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
                throw new ServiceException("comparator is null");
            }
        } else {
            throw new ServiceException("newsList is null");
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
}