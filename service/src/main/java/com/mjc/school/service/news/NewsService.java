package com.mjc.school.service.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;

import java.util.List;

/**
 * The interface News service.
 */
public interface NewsService {
    /**
     * Create news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean create(News news) throws ServiceException, RepositoryException;

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteById(long newsId) throws ServiceException;

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteByAuthorId(long authorId) throws ServiceException;

    /**
     * Delete by news id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteByNewsIdFromTableNewsTags(long newsId) throws ServiceException;

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean update(News news) throws ServiceException;

    /**
     * Find all news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findAllNews() throws ServiceException;

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws ServiceException the service exception
     */
    public News findNewsById(long newsId) throws ServiceException;

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByTagName(String tagName) throws ServiceException;

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByTagId(long tagId) throws ServiceException;

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByAuthorName(String authorName) throws ServiceException;

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByPartOfTitle(String partOfTitle) throws ServiceException;

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findNewsByPartOfContent(String partOfContent) throws ServiceException;

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNews(List<News> newsList, SortNewsComparator comparator) throws ServiceException;

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByCreatedDateTimeAsc(List<News> newsList) throws ServiceException;

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByCreatedDateTimeDesc(List<News> newsList) throws ServiceException;

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByModifiedDateTimeAsc(List<News> newsList) throws ServiceException;

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortNewsByModifiedDateTimeDesc(List<News> newsList) throws ServiceException;
}