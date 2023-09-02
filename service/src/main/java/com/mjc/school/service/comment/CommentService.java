package com.mjc.school.service.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.exception.SortException;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;

import java.util.List;

/**
 * The interface Comment service.
 */
public interface CommentService {
    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> findCommentsByNewsId(long newsId) throws ServiceException;

    /**
     * Find all comments list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> findAllComments() throws ServiceException;

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException the service exception
     */
    public Comment findCommentById(long id) throws ServiceException;

    /**
     * Sort list.
     *
     * @param list       the list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sort(List<Comment> list, SortCommentComparator comparator) throws ServiceException;

    /**
     * Sort by created date time asc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByCreatedDateTimeAsc(List<Comment> list) throws ServiceException;

    /**
     * Sort by created date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list) throws ServiceException;

    /**
     * Sort by modified date time asc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list) throws ServiceException;

    /**
     * Sort by modified date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws ServiceException;

    /**
     * Delete by news id boolean.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteByNewsId(long newsId) throws ServiceException;
}