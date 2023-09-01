package com.mjc.school.service.tag;

import com.mjc.school.entity.Tag;

import java.util.List;

public interface TagService {
    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    public boolean create(Tag tag);

    /**
     * Delete by id boolean.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    public boolean deleteById(long tagId);

    /**
     * Delete tags by tag id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    public boolean deleteByTagIdFromTableTagsNews(long tagId);

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    public boolean update(Tag tag);

    /**
     * Find all tags list.
     *
     * @return the list
     */
    public List<Tag> findAllTags();

    /**
     * Find tags by id tag.
     *
     * @param id the id
     * @return the tag
     */
    public Tag findById(long id);

    /**
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     */
    public List<Tag> findByPartOfName(String partOfName);

    /**
     * Find tags by news id list.
     *
     * @param newsId the news id
     * @return the list
     */
    public List<Tag> findByNewsId(long newsId);
}