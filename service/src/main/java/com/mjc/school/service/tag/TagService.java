package com.mjc.school.service.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws ServiceException            the service exception
     */
    public boolean create(Tag tag)
            throws IncorrectParameterException, ServiceException;

    /**
     * Delete tag by id.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteById(long tagId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Delete tag by id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean deleteByTagIdFromTableTagsNews(long tagId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean update(Tag tag)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find all tags.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Tag> findAllTags()
            throws ServiceException;

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the tag
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public Tag findById(long id)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find tags by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<Tag> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException;

    /**
     * Find tags by news id.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public List<Tag> findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;
}