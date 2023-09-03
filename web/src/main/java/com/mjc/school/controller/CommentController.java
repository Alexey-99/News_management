package com.mjc.school.controller;

import com.mjc.school.entity.Comment;
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
    public List<Comment> findAll() throws ServiceException {
        return commentService.findAll();
    }

    /**
     * Find comments by news id.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public List<Comment> findByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        return commentService.findByNewsId(newsId);
    }

    /**
     * Find comment by id.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-id/{id}")
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
    public List<Comment> sortByCreatedDateTimeAsc()
            throws ServiceException {
        return commentService.sortByCreatedDateTimeAsc(commentService.findAll());
    }

    /**
     * Sort comments by created date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/desc")
    public List<Comment> sortByCreatedDateTimeDesc()
            throws ServiceException {
        return commentService.sortByCreatedDateTimeDesc(commentService.findAll());
    }

    /**
     * Sort comments by modified date time asc.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/asc")
    public List<Comment> sortByModifiedDateTimeAsc()
            throws ServiceException {
        return commentService.sortByModifiedDateTimeAsc(commentService.findAll());
    }

    /**
     * Sort comments by modified date time desc.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/desc")
    public List<Comment> sortByModifiedDateTimeDesc()
            throws ServiceException {
        return commentService.sortByModifiedDateTimeDesc(commentService.findAll());
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
    @DeleteMapping("/delete-by-id/{id}")
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

    /**
     * Get objects from list.
     *
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    @GetMapping("/pagination")
    public List<Comment> getEntity(@RequestParam(value = "count-elements-on-page")
                                   long numberElementsReturn,
                                   @RequestParam(value = "number-page",
                                           required = false,
                                           defaultValue = "0")
                                   long numberPage) throws ServiceException {
        return commentService.getEntity(commentService.findAll(),
                numberElementsReturn, numberPage);
    }
}