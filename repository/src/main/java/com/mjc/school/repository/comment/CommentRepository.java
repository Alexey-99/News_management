package com.mjc.school.repository.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.RepositoryException;

import java.util.List;

/**
 * The interface Comment repository.
 */
public interface CommentRepository {

    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Comment> findByNewsId(long newsId) throws RepositoryException;

    /**
     * Find all comments list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Comment> findAll() throws RepositoryException;

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws RepositoryException the repository exception
     */
    public Comment findById(long id) throws RepositoryException;

    /**
     * Create comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean create(Comment comment) throws RepositoryException;

    /**
     * Update comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean update(Comment comment) throws RepositoryException;

    /**
     * Delete comment by id.
     *
     * @param id the id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteById(long id) throws RepositoryException;

    /**
     * Delete comments by news id.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteByNewsId(long newsId) throws RepositoryException;
}