package com.mjc.school.service.tag;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.TagDTO;

import java.util.List;

public interface TagService
        extends PaginationService<TagDTO>,
        CRUDOperationService<TagDTO> {
    public boolean addToNews(long tagId, long newsId)
            throws ServiceException, IncorrectParameterException;

    public boolean removeFromNews(long tagId, long newsId)
            throws ServiceException, IncorrectParameterException;

    public boolean deleteFromAllNews(long tagId)
            throws ServiceException, IncorrectParameterException;

    public List<TagDTO> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException;

    public List<TagDTO> findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException;
}