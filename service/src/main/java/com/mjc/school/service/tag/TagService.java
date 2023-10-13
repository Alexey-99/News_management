package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.validation.dto.TagDTO;

import java.util.List;

public interface TagService
        extends CRUDOperationService<TagDTO> {
    boolean addToNews(long tagId, long newsId)
            throws ServiceException;

    boolean removeFromNews(long tagId, long newsId)
            throws ServiceException;

    boolean deleteFromAllNews(long tagId)
            throws ServiceException;

    List<TagDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException;

    List<TagDTO> findByNewsId(long newsId, int page, int size)
            throws ServiceException;
}