package com.mjc.school.service.comment.impl;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.SortException;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeAsc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Comment service.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     */
    @Override
    public List<Comment> findCommentsByNewsId(long newsId) {
        return commentRepository.findCommentsByNewsId(newsId);
    }

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     */
    @Override
    public Comment findCommentById(long id) {
        return commentRepository.findCommentById(id);
    }

    /**
     * Sort comment list.
     *
     * @param list       the comments list
     * @param comparator the comparator
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<Comment> sort(List<Comment> list, SortCommentComparator comparator)
            throws SortException {
        List<Comment> sortedList;
        if (list != null) {
            if (comparator != null) {
                sortedList = new LinkedList<>(list);
                sortedList.sort(comparator);
            } else {
                throw new SortException("comparator is null");
            }
        } else {
            throw new SortException("list is null");
        }
        return sortedList;
    }

    /**
     * Sort comment by created date time asc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<Comment> sortByCreatedDateTimeAsc(List<Comment> list)
            throws SortException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeAsc());
    }

    /**
     * Sort comment by created date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list)
            throws SortException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeDesc());
    }

    /**
     * Sort comment by modified date time asc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list)
            throws SortException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeAsc());
    }

    /**
     * Sort comment by modified date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list)
            throws SortException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeDesc());
    }

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     */
    @Override
    public boolean deleteByNewsId(long newsId) {
        return commentRepository.deleteByNewsId(newsId);
    }
}