package com.mjc.school.service.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;

import java.util.List;

public interface CommentService {
    public List<Comment> findCommentsByNewsId(long newsId) throws ServiceException;

    public List<Comment> findAllComments() throws ServiceException;

    public Comment findCommentById(long id) throws ServiceException;

    public List<Comment> sort(List<Comment> list, SortCommentComparator comparator) throws ServiceException;

    public List<Comment> sortByCreatedDateTimeAsc(List<Comment> list) throws ServiceException;

    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list) throws ServiceException;

    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list) throws ServiceException;

    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws ServiceException;

    public boolean deleteByNewsId(long newsId) throws ServiceException;
}