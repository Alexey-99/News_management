package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.BaseService;
import com.mjc.school.validation.dto.TagDTO;

import java.util.List;

public interface TagService extends BaseService<TagDTO> {
    boolean addToNews(long tagId, long newsId);

    boolean deleteFromNews(long tagId, long newsId);

    boolean deleteFromAllNews(long tagId);

    List<TagDTO> findByPartOfName(String partOfName,
                                  int page, int size)
            throws ServiceException;

    List<TagDTO> findByPartOfName(String partOfName);

    List<TagDTO> findByNewsId(long newsId,
                              int page, int size)
            throws ServiceException;

    List<TagDTO> findByNewsId(long newsId);
}