package com.mjc.school.service.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.SortException;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;

import java.util.List;

public interface CommentService {
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
     * Sort comment list.
     *
     * @param list       the comments list
     * @param comparator the comparator
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sort(List<Comment> list, SortCommentComparator comparator) throws SortException;

    /**
     * Sort comment by created date time asc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByCreatedDateTimeAsc(List<Comment> list) throws SortException;

    /**
     * Sort comment by created date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list) throws SortException;

    /**
     * Sort comment by modified date time asc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list) throws SortException;

    /**
     * Sort comment by modified date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws SortException;

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     */
    public boolean deleteByNewsId(long newsId);
}