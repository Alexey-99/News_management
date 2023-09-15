package com.mjc.school.repository.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

/**
 * The interface Tag repository.
 */
public interface TagRepository extends CRUDOperationRepository<Tag> {
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
     * Delete by tag id from table tags news tag.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteAllTagsFromNewsByNewsId(long tagId) throws RepositoryException;

    /**
     * Find by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Tag> findByNewsId(long newsId) throws RepositoryException;

    /**
     * Is exists tag with name.
     *
     * @param name the tag name
     * @return true - if exists news with title, false - if not exists
     * @throws RepositoryException the repository exception
     */
    public boolean isExistsTagWithName(String name) throws RepositoryException;
}