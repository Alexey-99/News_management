package com.mjc.school.service.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService
        extends PaginationService<Tag>, CRUDOperationService<Tag> {
    /**
     * Add tag to news.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean addToNews(long tagId, long newsId)
            throws ServiceException, IncorrectParameterException;

    /**
     * Remove tag from news.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean removeFromNews(long tagId, long newsId)
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