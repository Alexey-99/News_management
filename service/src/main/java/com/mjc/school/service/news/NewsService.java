package com.mjc.school.service.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.SortException;
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
     */
    public boolean create(News news);


    /**
     * Delete news by id .
     *
     * @param newsId the news id
     * @return the boolean
     */
    public boolean deleteById(long newsId);

    /**
     * Delete news by author id.
     *
     * @param authorId the author id
     * @return the boolean
     */
    public boolean deleteByAuthorId(long authorId);


    /**
     * Delete news by news id from table news tags.
     *
     * @param newsId the news id
     * @return the boolean
     */
    public boolean deleteByNewsIdFromTableNewsTags(long newsId);

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     */
    public boolean update(News news);

    /**
     * Find all news list.
     *
     * @return the list
     */
    public List<News> findAllNews();


    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     */
    public News findNewsById(long newsId);


    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     */
    public List<News> findNewsByTagName(String tagName);


    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     */
    public List<News> findNewsByTagId(long tagId);


    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     */
    public List<News> findNewsByAuthorName(String authorName);


    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     */
    public List<News> findNewsByPartOfTitle(String partOfTitle);


    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     */
    public List<News> findNewsByPartOfContent(String partOfContent);

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws SortException the sort exception
     */
    public List<News> sortNews(List<News> newsList, SortNewsComparator comparator) throws SortException;

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<News> sortNewsByCreatedDateTimeAsc(List<News> newsList) throws SortException;

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<News> sortNewsByCreatedDateTimeDesc(List<News> newsList) throws SortException;

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<News> sortNewsByModifiedDateTimeAsc(List<News> newsList) throws SortException;

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<News> sortNewsByModifiedDateTimeDesc(List<News> newsList) throws SortException;
}