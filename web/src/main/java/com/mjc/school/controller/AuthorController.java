package com.mjc.school.controller;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public Pagination<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsWithAmountOfWrittenNews(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return authorService.getPaginationAuthorIdWithAmountOfWrittenNews(
                authorService.selectAllAuthorsIdWithAmountOfWrittenNews(),
                countElementsReturn,
                numberPage);
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
        return new ResponseEntity<>(result, CREATED);
    }

    /**
     * Delete author.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.delete(id);
        return new ResponseEntity<>(result, OK);
    }

    /**
     * Update author.
     *
     * @param author the author
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Author author)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.update(author);
        return new ResponseEntity<>(result, OK);
    }

    /**
     * Find all authors list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/all")
    public Pagination<Author> findAllAuthors(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return authorService.getPagination(
                authorService.findAllAuthors(),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/id/{id}")
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
    @GetMapping("/part-of-name/{partOfName}")
    public Pagination<Author> findByPartOfName(
            @PathVariable String partOfName,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return authorService.getPagination(
                authorService.findByPartOfName(partOfName),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/news-id/{newsId}")
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
    @GetMapping("/sort/amount-news")
    public Pagination<AuthorIdWithAmountOfWrittenNews>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return authorService.getPaginationAuthorIdWithAmountOfWrittenNews(
                authorService.sortAllAuthorsIdWithAmountOfWrittenNewsDesc(),
                countElementsReturn,
                numberPage);
    }
}