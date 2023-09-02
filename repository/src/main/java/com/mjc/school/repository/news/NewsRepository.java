package com.mjc.school.repository.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;

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
     * @throws RepositoryException the repository exception
     */
    public boolean create(News news) throws RepositoryException;

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteById(long newsId) throws RepositoryException;

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteByAuthorId(long authorId) throws RepositoryException;

    /**
     * Delete by news id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteByNewsIdFromTableNewsTags(long newsId) throws RepositoryException;

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean update(News news) throws RepositoryException;

    /**
     * Find all news list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<News> findAllNews() throws RepositoryException;

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws RepositoryException the repository exception
     */
    public News findNewsById(long newsId) throws RepositoryException;

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<News> findNewsByTagName(String tagName) throws RepositoryException;

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<News> findNewsByTagId(long tagId) throws RepositoryException;

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<News> findNewsByAuthorName(String authorName) throws RepositoryException;

    /**
     * Find news by author id list.
     *
     * @param authorId the author id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<News> findNewsByAuthorId(long authorId) throws RepositoryException;
}