package com.mjc.school.controller;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.SortException;
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
     * Select all authors with amount of written news map.
     *
     * @return the map
     */
    @GetMapping("/author-amount-news")
    public List<Entry<Author, Long>> selectAllAuthorsWithAmountOfWrittenNews() { // +
        return authorService.selectAllAuthorsWithAmountOfWrittenNews();
    }

    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     */
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Author author) {
        authorService.create(author);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) { // +
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Author author) {
        authorService.update(author);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Find all authors list.
     *
     * @return the list
     */
    @GetMapping("/all")
    public List<Author> findAllAuthors() { // +
        return authorService.findAllAuthors();
    }

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    @GetMapping("/get-by-id/{id}")
    public Author findById(@PathVariable long id) { //+
        return authorService.findById(id);
    }

    /**
     * Find authors by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    @GetMapping("/get-by-name/{partOfName}") // +
    public List<Author> findByPartOfName(@PathVariable String partOfName) {
        return authorService.findByPartOfName(partOfName);
    }

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    @GetMapping("/get-by-news-id/{newsId}") // +
    public Author findByNewsId(@PathVariable long newsId) {
        return authorService.findByNewsId(newsId);
    }

    /**
     * Sort all authors with amount of written news desc map.
     *
     * @return the map
     */
    @GetMapping("/sort")
    public List<Entry<Author, Long>> sortAllAuthorsWithAmountOfWrittenNewsDesc()
            throws SortException { //+
        return authorService.sortAllAuthorsWithAmountOfWrittenNewsDesc();
    }
}