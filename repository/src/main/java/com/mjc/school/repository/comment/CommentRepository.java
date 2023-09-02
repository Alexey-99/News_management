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
    public List<Comment> findCommentsByNewsId(long newsId) throws RepositoryException;

    /**
     * Find all comments list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Comment> findAllComments() throws RepositoryException;

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws RepositoryException the repository exception
     */
    public Comment findCommentById(long id) throws RepositoryException;

    /**
     * Delete by news id boolean.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteByNewsId(long newsId) throws RepositoryException;
}