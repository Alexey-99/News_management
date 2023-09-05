package com.mjc.school.controller;

import com.mjc.school.entity.News;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.news.NewsService;
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

/**
 * The type News controller.
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * Create response entity.
     *
     * @param news the news
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody News news)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.create(news);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Delete by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete by author id response entity.
     *
     * @param authorId the author id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/author-id/{authorId}")
    public ResponseEntity<Boolean> deleteByAuthorId(@PathVariable long authorId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteByAuthorId(authorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete by news id from table news tags response entity.
     *
     * @param newsId the news id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/id-with-tags/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsIdFromTableNewsTags(
            @PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteByIdFromTableNewsTags(newsId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update boolean.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PutMapping("/update")
    public boolean update(@RequestBody News news)
            throws ServiceException, IncorrectParameterException {
        return newsService.update(news);
    }

    /**
     * Find all news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/all")
    public Pagination<News> findAllNews(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findAll(),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find news by id news.
     *
     * @param id the id
     * @return the news
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/id/{id}")
    public News findNewsById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return newsService.findById(id);
    }

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/tag-name/{tagName}")
    public Pagination<News> findNewsByTagName(
            @PathVariable String tagName,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagName(tagName),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/tag-id/{tagId}")
    public Pagination<News> findNewsByTagId(
            @PathVariable long tagId,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagId(tagId),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/author-name/{authorName}")
    public Pagination<News> findNewsByAuthorName(
            @PathVariable String authorName,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByAuthorName(authorName),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/part-of-title/{partOfTitle}")
    public Pagination<News> findNewsByPartOfTitle(
            @PathVariable String partOfTitle,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByPartOfTitle(partOfTitle),
                countElementsReturn,
                numberPage);
    }

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/part-of-content/{partOfContent}")
    public Pagination<News> findNewsByPartOfContent(
            @PathVariable String partOfContent,
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByPartOfContent(partOfContent),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort news by created date time asc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/asc")
    public Pagination<News> sortNewsByCreatedDateTimeAsc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.sortByCreatedDateTimeAsc(
                        newsService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort news by created date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/desc")
    public Pagination<News> sortNewsByCreatedDateTimeDesc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.sortByCreatedDateTimeDesc(
                        newsService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/asc")
    public Pagination<News> sortNewsByModifiedDateTimeAsc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.sortByModifiedDateTimeAsc(
                        newsService.findAll()),
                countElementsReturn,
                numberPage);
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/desc")
    public Pagination<News> sortNewsByModifiedDateTimeDesc(
            @RequestParam(value = "count-elements-on-page",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "number-page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.sortByModifiedDateTimeDesc(
                        newsService.findAll()),
                countElementsReturn,
                numberPage);
    }
}