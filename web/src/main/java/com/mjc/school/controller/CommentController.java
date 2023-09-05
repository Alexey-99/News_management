package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortingField;
import com.mjc.school.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mjc.school.name.SortingField.MODIFIED;
import static com.mjc.school.name.SortingType.ASCENDING;
import static com.mjc.school.name.SortingType.DESCENDING;
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
     * @param countElementsReturn the count elements return
     * @param numberPage          the number page
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
     * @param newsId              the news id
     * @param countElementsReturn the count elements return
     * @param numberPage          the number page
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
     * Sort pagination.
     *
     * @param countElementsReturn the count elements on page
     * @param numberPage          the number page
     * @param sortingField        the sorting field
     * @param sortingType         the sorting type
     * @return the pagination
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort")
    public Pagination<Comment> sort(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage,
            @RequestParam(value = "sorting-field",
                    required = false,
                    defaultValue = MODIFIED)
            String sortingField,
            @RequestParam(value = "sorting-type",
                    required = false,
                    defaultValue = DESCENDING)
            String sortingType)
            throws ServiceException {
        Pagination<Comment> sortedList = null;
        switch (sortingField) {
            case SortingField.CREATED -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = commentService.getPagination(
                            commentService.sortByCreatedDateTimeAsc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = commentService.getPagination(
                            commentService.sortByCreatedDateTimeDesc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
            default -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = commentService.getPagination(
                            commentService.sortByModifiedDateTimeAsc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = commentService.getPagination(
                            commentService.sortByModifiedDateTimeDesc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
        }
        return sortedList;
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
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PutMapping("/update")
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
    @DeleteMapping("/news-id/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.deleteByNewsId(newsId);
        return new ResponseEntity<>(result, OK);
    }
}