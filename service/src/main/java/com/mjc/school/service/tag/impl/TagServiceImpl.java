package com.mjc.school.service.tag.impl;

import com.mjc.school.entity.Tag;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    @Override
    public boolean create(Tag tag) {
        return tagRepository.create(tag);
    }

    /**
     * Delete by id boolean.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    @Override
    public boolean deleteById(long tagId) {
        tagRepository.deleteByTagIdFromTableTagsNews(tagId);
        return tagRepository.deleteById(tagId);
    }

    /**
     * Delete tags by tag id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    @Override
    public boolean deleteByTagIdFromTableTagsNews(long tagId) {
        return tagRepository.deleteByTagIdFromTableTagsNews(tagId);
    }

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    @Override
    public boolean update(Tag tag) {
        return tagRepository.update(tag);
    }

    /**
     * Find all tags list.
     *
     * @return the list
     */
    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAllTags();
    }

    /**
     * Find tags by id tag.
     *
     * @param id the id
     * @return the tag
     */
    @Override
    public Tag findById(long id) {
        return tagRepository.findById(id);
    }

    /**
     * Find by part of name tag.
     *
     * @param partOfName the part of name
     * @return the tag
     */
    @Override
    public List<Tag> findByPartOfName(String partOfName) {
        Pattern p = Pattern.compile(partOfName);
        return tagRepository.findAllTags()
                .stream()
                .filter(tag ->
                        (p.matcher(tag.getName()).find())
                                || (p.matcher(tag.getName()).lookingAt())
                                || (tag.getName().matches(partOfName))
                ).toList();
    }

    /**
     * Find tags by news id list.
     *
     * @param newsId the news id
     * @return the list
     */
    @Override
    public List<Tag> findByNewsId(long newsId) {
        return tagRepository.findByNewsId(newsId);
    }
}