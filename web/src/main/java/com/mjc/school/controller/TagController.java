package com.mjc.school.controller;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.tag.TagService;
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
 * The type Tag controller.
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody Tag tag)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.create(tag);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Delete tag by id.
     *
     * @param id the tag id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.deleteById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Delete tag by id from table tags news.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/delete-by-id/table_news_tags/{id}")
    public ResponseEntity<Boolean> deleteByTagIdFromTableTagsNews(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.deleteByTagIdFromTableTagsNews(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the response entity
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @DeleteMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Tag tag)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.update(tag);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Find all tags.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @GetMapping("/all")
    public List<Tag> findAll() throws ServiceException {
        return tagService.findAllTags();
    }

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the tag
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-id/{id}")
    public Tag findById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return tagService.findById(id);
    }

    /**
     * Find tags by part of tag name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-part-of-name/{partOfName}")
    public List<Tag> findByPartOfName(@PathVariable String partOfName)
            throws ServiceException, IncorrectParameterException {
        return tagService.findByPartOfName(partOfName);
    }

    /**
     * Find tags by news id.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @GetMapping("/get-by-news-id/{newsId}")
    public List<Tag> findByNewsId(@PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        return tagService.findByNewsId(newsId);
    }
}