package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public List<Comment> findCommentsByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        return commentService.findByNewsId(newsId);
    }

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-id/{id}")
    public Comment findCommentById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return commentService.findById(id);
    }

    /**
     * Sort by created date time asc list.
     *
     * @param sortBy   the sort by
     * @param sortType the sort type
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/asc")
    public List<Comment> sortByCreatedDateTimeAsc(
            @PathVariable String sortBy, @PathVariable String sortType)
            throws ServiceException {
        return commentService.sortByCreatedDateTimeAsc(commentService.findAll());
    }

    /**
     * Sort by created date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/desc")
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
    @GetMapping("/sort/modified/asc")
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
    @GetMapping("/sort/modified/desc")
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws ServiceException {
        return commentService.sortByModifiedDateTimeDesc(list);
    }

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/delete/{newsId}")
    public boolean deleteByNewsId(long newsId) throws ServiceException, IncorrectParameterException {
        return commentService.deleteByNewsId(newsId);
    }
}