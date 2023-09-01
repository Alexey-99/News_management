package com.mjc.school.repository.news;

import com.mjc.school.entity.News;

import java.util.List;

/**
 * The interface News repository.
 */
public interface NewsRepository {
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
}