package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.TagDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface TagService {
    boolean create(TagDTO tagDTO) throws ServiceException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    boolean deleteFromNews(long tagId, long newsId);

    @Transactional
    boolean deleteFromAllNews(long tagId);

    @Transactional
    TagDTO update(TagDTO tagDTO) throws ServiceException;

    boolean addToNews(long tagId, long newsId);

    List<TagDTO> findAll(int page, int size) throws ServiceException;

    List<TagDTO> findAll();

    TagDTO findById(long id) throws ServiceException;

    List<TagDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException;

    List<TagDTO> findByPartOfName(String partOfName);

    List<TagDTO> findByNewsId(long newsId, int page, int size)
            throws ServiceException;

    List<TagDTO> findByNewsId(long newsId);

    Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage,
                                     List<TagDTO> allElementsList,
                                     int page, int size);
}