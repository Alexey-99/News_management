package com.mjc.school.controller;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map.Entry;

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
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews() throws ServiceException {
        return authorService.selectAllAuthorsWithAmountOfWrittenNews();
    }


    /**
     * Create author.
     *
     * @param author the author
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PutMapping("/create")
    public ResponseEntity<String> create(@RequestBody Author author) throws ServiceException { //TODO
        authorService.create(author);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }


    /**
     * Delete author.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws ServiceException { //TODO
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }


    /**
     * Update author.
     *
     * @param author the author
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Author author) throws ServiceException { //TODO
        authorService.update(author);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
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
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-id/{id}")
    public Author findById(@PathVariable long id) throws ServiceException {
        return authorService.findById(id);
    }


    /**
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-name/{partOfName}")
    public List<Author> findByPartOfName(@PathVariable String partOfName) throws ServiceException {
        return authorService.findByPartOfName(partOfName);
    }


    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     * @throws ServiceException the service exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public Author findByNewsId(@PathVariable long newsId) throws ServiceException {
        return authorService.findByNewsId(newsId);
    }


    /**
     * Sort all authors with amount of written news desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort")
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc()
            throws ServiceException {
        return authorService.sortAllAuthorsWithAmountOfWrittenNewsDesc();
    }
}