package com.mjc.school.repository.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

/**
 * The interface Comment repository.
 */
public interface CommentRepository extends CRUDOperationRepository<Comment> {

    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<Comment> findByNewsId(long newsId) throws RepositoryException;

    /**
     * Delete comments by news id.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    public boolean deleteByNewsId(long newsId) throws RepositoryException;
}