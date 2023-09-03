package com.mjc.school.controller;

import com.mjc.school.entity.Tag;
import com.mjc.school.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    public boolean create(Tag tag) {
        return tagService.create(tag);
    }

    /**
     * Delete by id boolean.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    public boolean deleteById(long tagId) {
        return tagService.deleteById(tagId);
    }

    /**
     * Delete tags by tag id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    public boolean deleteByTagIdFromTableTagsNews(long tagId) {
        return tagService.deleteByTagIdFromTableTagsNews(tagId);
    }

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    public boolean update(Tag tag) {
        return tagService.update(tag);
    }

    /**
     * Find all tags list.
     *
     * @return the list
     */
    public List<Tag> findAllTags() {
        return tagService.findAllTags();
    }

    /**
     * Find tags by id tag.
     *
     * @param id the id
     * @return the tag
     */
    public Tag findById(long id) {
        return tagService.findById(id);
    }

    /**
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     */
    public List<Tag> findByPartOfName(String partOfName) {
        return tagService.findByPartOfName(partOfName);
    }

    /**
     * Find tags by news id list.
     *
     * @param newsId the news id
     * @return the list
     */
    public List<Tag> findByNewsId(long newsId) {
        return tagService.findByNewsId(newsId);
    }
}