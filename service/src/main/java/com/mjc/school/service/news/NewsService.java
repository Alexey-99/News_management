package com.mjc.school.service.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.pagination.Pagination;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;

import java.util.List;

/**
 * The interface News service.
 */
public interface NewsService extends Pagination<News> {
    /**
     * Create news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean create(News news)
            throws ServiceException, IncorrectParameterException;

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteById(long newsId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Delete by id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteByIdFromTableNewsTags(long newsId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean update(News news)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> findAll() throws ServiceException;

    /**
     * Find by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public News findById(long newsId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<News> findByTagName(String tagName)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<News> findByTagId(long tagId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<News> findByAuthorName(String authorName)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<News> findByPartOfTitle(String partOfTitle)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<News> findByPartOfContent(String partOfContent)
            throws ServiceException, IncorrectParameterException;

    /**
     * Sort list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sort(List<News> newsList,
                           SortNewsComparator comparator)
            throws ServiceException;

    /**
     * Sort by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortByCreatedDateTimeAsc(List<News> newsList)
            throws ServiceException;

    /**
     * Sort by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortByCreatedDateTimeDesc(List<News> newsList)
            throws ServiceException;

    /**
     * Sort by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortByModifiedDateTimeAsc(List<News> newsList)
            throws ServiceException;

    /**
     * Sort by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<News> sortByModifiedDateTimeDesc(List<News> newsList)
            throws ServiceException;
}