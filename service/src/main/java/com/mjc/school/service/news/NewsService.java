package com.mjc.school.service.news;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface NewsService {
    @Transactional
    boolean create(NewsDTO newsDTO) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    NewsDTO update(NewsDTO entity) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteByAuthorId(long authorId) throws ServiceBadRequestParameterException;

    @Transactional
    NewsDTO deleteAllTagsFromNews(long newsId) throws ServiceBadRequestParameterException;

    List<NewsDTO> findAll(int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    List<NewsDTO> findAll();

    long countAllNews();

    NewsDTO findById(long id) throws ServiceNoContentException;

    List<NewsDTO> findByTagName(String tagName, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByTagName(String tagName);

    List<NewsDTO> findByTagId(long tagId, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByTagId(long tagId);

    List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByPartOfAuthorName(String partOfAuthorName);

    List<NewsDTO> findByAuthorId(long authorId, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByAuthorId(long authorId);

    List<NewsDTO> findByPartOfTitle(String partOfTitle, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByPartOfTitle(String partOfTitle);

    List<NewsDTO> findByPartOfContent(String partOfContent, int page, int size, String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllNewsByPartOfContent(String partOfContent);

    Pagination<NewsDTO> getPagination(List<NewsDTO> elementsOnPage, long countAllElements, int page, int size);
}