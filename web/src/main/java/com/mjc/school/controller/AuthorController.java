package com.mjc.school.controller;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Author controller.
 */
@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    /**
     * Select all authors with amount of written news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/amount-news")
    public List<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsWithAmountOfWrittenNews()
            throws ServiceException {
        return authorService.selectAllAuthorsIdWithAmountOfWrittenNews();
    }

    /**
     * Create author.
     *
     * @param author the author
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody Author author)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.create(author);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Author author)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.update(author);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Find all authors list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/all")
    public List<Author> findAllAuthors() throws ServiceException {
        return authorService.findAllAuthors();
    }

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-id/{id}")
    public Author findById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return authorService.findById(id);
    }

    /**
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-part-of-name/{partOfName}")
    public List<Author> findByPartOfName(@PathVariable String partOfName)
            throws ServiceException, IncorrectParameterException {
        return authorService.findByPartOfName(partOfName);
    }

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public Author findByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        return authorService.findByNewsId(newsId);
    }

    /**
     * Sort all authors with amount of written news desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-amount-news")
    public List<AuthorIdWithAmountOfWrittenNews>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc() throws ServiceException {
        return authorService.sortAllAuthorsIdWithAmountOfWrittenNewsDesc();
    }
}