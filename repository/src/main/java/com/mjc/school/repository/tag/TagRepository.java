package com.mjc.school.repository.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.RepositoryException;

import java.util.List;

/**
 * The interface Tag repository.
 */
public interface TagRepository {
    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean create(Tag tag) throws RepositoryException;

    /**
     * Add tag to news by id.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean addToNews(long tagId, long newsId) throws RepositoryException;

    /**
     * Remove tag from news by id.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean removeFromNews(long tagId, long newsId) throws RepositoryException;

    /**
     * Delete tag by id.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteById(long tagId) throws RepositoryException;

    /**
     * Delete by tag id from table tags news tag.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteAllTagsFromNewsByNewsId(long tagId) throws RepositoryException;

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean update(Tag tag) throws RepositoryException;

    /**
     * Find all tags list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Tag> findAll() throws RepositoryException;

    /**
     * Find by id tag.
     *
     * @param id the id
     * @return the tag
     * @throws RepositoryException the repository exception
     */
    public Tag findById(long id) throws RepositoryException;

    /**
     * Find by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Tag> findByNewsId(long newsId) throws RepositoryException;
}