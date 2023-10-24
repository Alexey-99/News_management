package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.TagDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface TagService {
    @Transactional
    boolean create(TagDTO tagDTO) throws ServiceException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    boolean deleteFromNews(long tagId, long newsId) throws ServiceException;

    @Transactional
    boolean deleteFromAllNews(long tagId) throws ServiceException;

    @Transactional
    TagDTO update(TagDTO tagDTO) throws ServiceException;

    boolean addToNews(long tagId, long newsId) throws ServiceException;

    List<TagDTO> findAll(int page, int size, String sortField, String sortType) throws ServiceException;

    List<TagDTO> findAll();

    long countAll();

    TagDTO findById(long id) throws ServiceException;

    List<TagDTO> findByPartOfName(String partOfName, int page, int size, String sortField, String sortType) throws ServiceException;

    long countAllByPartOfName(String partOfName);

    List<TagDTO> findByNewsId(long newsId, int page, int size, String sortField, String sortType) throws ServiceException;

    long countAllByNewsId(long newsId);

    Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage, long countAllElements, int page, int size);
}