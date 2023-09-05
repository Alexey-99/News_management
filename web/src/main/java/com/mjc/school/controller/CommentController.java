package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Comment controller.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Find all comments.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/all")
    public Pagination<Comment> findAll(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.findAll(),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find comments by news id.
     *
     * @param newsId the news id
     * @return the list•
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/news-id/{newsId}")
    public Pagination<Comment> findByNewsId(
            @PathVariable long newsId,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return commentService.getPagination(
                commentService.findByNewsId(newsId),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find comment by id.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/id/{id}")
    public Comment findById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return commentService.findById(id);
    }

    /**
     * Sort comments by created date time asc.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/asc")
    public Pagination<Comment> sortByCreatedDateTimeAsc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.sortByCreatedDateTimeAsc(
                        commentService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort comments by created date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/desc")
    public Pagination<Comment> sortByCreatedDateTimeDesc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.sortByCreatedDateTimeDesc(
                        commentService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort comments by modified date time asc.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/{searchType}")
    public Pagination<Comment> sortByModifiedDateTimeAsc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.sortByModifiedDateTimeAsc(
                        commentService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort comments by modified date time desc.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/desc")
    public Pagination<Comment> sortByModifiedDateTimeDesc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.sortByModifiedDateTimeDesc(
                        commentService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Create comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody Comment comment)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.create(comment);
        return new ResponseEntity<>(result, CREATED);
    }

    /**
     * Update comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Comment comment)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.update(comment);
        return new ResponseEntity<>(result, OK);
    }

    /**
     * Delete comment by id.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    /**
     * Delete comment by news id .
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/delete-by-news-id/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.deleteByNewsId(newsId);
        return new ResponseEntity<>(result, OK);
    }
}