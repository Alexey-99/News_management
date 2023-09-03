package com.mjc.school.controller;

import com.mjc.school.entity.News;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @DeleteMapping("/delete/by-id/{id}")
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
    @DeleteMapping("/delete/by-author-id/{authorId}")
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
    @DeleteMapping("/delete/by-id-with-tags/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsIdFromTableNewsTags(@PathVariable long newsId)
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
    @PostMapping("/update")
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
    public List<News> findAllNews() throws ServiceException {
        return newsService.findAll();
    }

    /**
     * Find news by id news.
     *
     * @param id the id
     * @return the news
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-id/{id}")
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
    @GetMapping("/get-by-tag-name/{tagName}")
    public List<News> findNewsByTagName(@PathVariable String tagName)
            throws ServiceException, IncorrectParameterException {
        return newsService.findByTagName(tagName);
    }

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-tag-id/{tagId}")
    public List<News> findNewsByTagId(@PathVariable long tagId)
            throws ServiceException, IncorrectParameterException {
        return newsService.findByTagId(tagId);
    }

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-author-name/{authorName}")
    public List<News> findNewsByAuthorName(@PathVariable String authorName)
            throws ServiceException, IncorrectParameterException {
        return newsService.findByAuthorName(authorName);
    }

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-part-of-title/{partOfTitle}")
    public List<News> findNewsByPartOfTitle(@PathVariable String partOfTitle)
            throws ServiceException, IncorrectParameterException {
        return newsService.findByPartOfTitle(partOfTitle);
    }

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-part-of-content/{partOfContent}")
    public List<News> findNewsByPartOfContent(@PathVariable String partOfContent)
            throws ServiceException, IncorrectParameterException {
        return newsService.findByPartOfContent(partOfContent);
    }

    /**
     * Sort news by created date time asc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/asc")
    public List<News> sortNewsByCreatedDateTimeAsc()
            throws ServiceException {
        return newsService.sortByCreatedDateTimeAsc(
                newsService.findAll());
    }

    /**
     * Sort news by created date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/created/desc")
    public List<News> sortNewsByCreatedDateTimeDesc()
            throws ServiceException {
        return newsService.sortByCreatedDateTimeDesc(
                newsService.findAll());
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/asc")
    public List<News> sortNewsByModifiedDateTimeAsc()
            throws ServiceException {
        return newsService.sortByModifiedDateTimeAsc(
                newsService.findAll());
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort/modified/desc")
    public List<News> sortNewsByModifiedDateTimeDesc()
            throws ServiceException {
        return newsService.sortByModifiedDateTimeDesc(
                newsService.findAll());
    }
}