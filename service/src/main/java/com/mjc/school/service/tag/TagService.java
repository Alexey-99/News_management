package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.service.tag.impl.sort.TagSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.TagDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TagService {
    @Transactional
    boolean create(TagDTO tagDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    boolean deleteFromNews(long tagId, long newsId) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteFromAllNews(long tagId) throws ServiceBadRequestParameterException;

    @Transactional
    TagDTO update(TagDTO tagDTO) throws ServiceBadRequestParameterException;

    boolean addToNews(long tagId, long newsId) throws ServiceBadRequestParameterException;

    List<TagDTO> findAll(int page, int size, String sortField, String sortType) throws ServiceNoContentException;

    List<TagDTO> findAll();

    long countAll();

    TagDTO findById(long id) throws ServiceNoContentException;

    List<TagDTO> findByPartOfName(String partOfName, int page, int size, String sortField, String sortType) throws ServiceNoContentException;

    long countAllByPartOfName(String partOfName);

    List<TagDTO> findByNewsId(long newsId, int page, int size, String sortField, String sortType) throws ServiceNoContentException;

    long countAllByNewsId(long newsId);

    Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage, long countAllElements, int page, int size);

    Optional<TagSortField> getOptionalSortField(String sortField);
}