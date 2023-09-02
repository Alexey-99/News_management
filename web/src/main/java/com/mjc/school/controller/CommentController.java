package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.exception.SortException;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Comment controller.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public List<Comment> findCommentsByNewsId(@PathVariable long newsId) throws ServiceException {
        return commentService.findCommentsByNewsId(newsId);
    }

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-id/{id}")
    public Comment findCommentById(@PathVariable long id) throws ServiceException {
        return commentService.findCommentById(id);
    }

    /**
     * Sort by created date time asc list.
     *
     * @param sortBy   the sort by
     * @param sortType the sort type
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort?by={sortBy}&type={sortType}")
    public List<Comment> sortByCreatedDateTimeAsc(@PathVariable String sortBy,
                                                  @PathVariable String sortType) throws ServiceException {
        System.out.println(sortBy);
        System.out.println(sortType);
        return commentService.sortByCreatedDateTimeAsc(commentService.findAllComments());
    }

    /**
     * Sort by created date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list) throws ServiceException {
        return commentService.sortByCreatedDateTimeDesc(list);
    }

    /**
     * Sort by modified date time asc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list) throws ServiceException {
        return commentService.sortByModifiedDateTimeAsc(list);
    }

    /**
     * Sort by modified date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws ServiceException {
        return commentService.sortByModifiedDateTimeDesc(list);
    }

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteByNewsId(long newsId) throws ServiceException {
        return commentService.deleteByNewsId(newsId);
    }
}