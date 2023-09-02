package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
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
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public List<Comment> findCommentsByNewsId(@PathVariable long newsId) {
        return commentService.findCommentsByNewsId(newsId);
    }

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     */
    @GetMapping("/get-by-id/{id}")
    public Comment findCommentById(@PathVariable long id) {
        return commentService.findCommentById(id);
    }

    /**
     * Sort comment by created date time asc list.
     *
     * @return the list
     * @throws SortException the sort exception
     */
    @GetMapping("/sort?by={sortBy}&type={sortType}")
    public List<Comment> sortByCreatedDateTimeAsc(@PathVariable String sortBy,
                                                  @PathVariable String sortType) throws SortException {
        System.out.println(sortBy);
        System.out.println(sortType);
        return commentService.sortByCreatedDateTimeAsc(commentService.findAllComments());
    }

    /**
     * Sort comment by created date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list) throws SortException {
        return commentService.sortByCreatedDateTimeDesc(list);
    }

    /**
     * Sort comment by modified date time asc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list) throws SortException {
        return commentService.sortByModifiedDateTimeAsc(list);
    }

    /**
     * Sort comment by modified date time desc list.
     *
     * @param list the comments list
     * @return the list
     * @throws SortException the sort exception
     */
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list) throws SortException {
        return commentService.sortByModifiedDateTimeDesc(list);
    }

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     */
    public boolean deleteByNewsId(long newsId) {
        return commentService.deleteByNewsId(newsId);
    }
}