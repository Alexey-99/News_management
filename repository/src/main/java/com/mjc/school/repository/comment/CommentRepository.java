package com.mjc.school.repository.comment;

import com.mjc.school.entity.Comment;

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
     */
    public List<Comment> findCommentsByNewsId(long newsId);

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     */
    public Comment findCommentById(long id);

    /**
     * Delete by news id boolean.
     *
     * @param newsId the news id
     * @return the boolean
     */
    public boolean deleteByNewsId(long newsId);
}