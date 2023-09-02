package com.mjc.school.controller;

import com.mjc.school.entity.News;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    public boolean create(News news)
            throws ServiceException, IncorrectParameterException {
        return newsService.create(news);
    }

    public boolean deleteById(long newsId)
            throws ServiceException, IncorrectParameterException {
        return newsService.deleteById(newsId);
    }

    public boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException {
        return newsService.deleteByAuthorId(authorId);
    }

    public boolean deleteByNewsIdFromTableNewsTags(long newsId)
            throws ServiceException, IncorrectParameterException {
        return newsService.deleteByIdFromTableNewsTags(newsId);
    }

    public boolean update(News news)
            throws ServiceException, IncorrectParameterException {
        return newsService.update(news);
    }

    /**
     * Find all news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findAllNews() throws ServiceException {
        return newsService.findAll();
    }

    public News findNewsById(long newsId)
            throws ServiceException, IncorrectParameterException {
        return newsService.findById(newsId);
    }

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByTagName(String tagName) throws ServiceException {
        return newsService.findByTagName(tagName);
    }

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByTagId(long tagId) throws ServiceException {
        return newsService.findByTagId(tagId);
    }

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByAuthorName(String authorName) throws ServiceException {
        return newsService.findByAuthorName(authorName);
    }

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByPartOfTitle(String partOfTitle) throws ServiceException {
        return newsService.findByPartOfTitle(partOfTitle);
    }

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByPartOfContent(String partOfContent) throws ServiceException {
        return newsService.findByPartOfContent(partOfContent);
    }

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNews(List<News> newsList, SortNewsComparator comparator) throws ServiceException {
        return newsService.sort(newsList, comparator);
    }

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByCreatedDateTimeAsc(List<News> newsList) throws ServiceException {
        return newsService.sortByCreatedDateTimeAsc(newsList);
    }

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByCreatedDateTimeDesc(List<News> newsList) throws ServiceException {
        return newsService.sortByCreatedDateTimeDesc(newsList);
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByModifiedDateTimeAsc(List<News> newsList) throws ServiceException {
        return newsService.sortByModifiedDateTimeAsc(newsList);
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByModifiedDateTimeDesc(List<News> newsList) throws ServiceException {
        return newsService.sortByModifiedDateTimeDesc(newsList);
    }
}