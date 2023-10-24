package com.mjc.school.service.news;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;

import javax.transaction.Transactional;
import java.util.List;

public interface NewsService {
    @Transactional
    boolean create(NewsDTO newsDTO) throws ServiceException;

    @Transactional
    boolean deleteById(long id);

    @Transactional
    NewsDTO update(NewsDTO entity) throws ServiceException;

    @Transactional
    boolean deleteByAuthorId(long authorId) throws ServiceException;

    @Transactional
    NewsDTO deleteAllTagsFromNews(long newsId) throws ServiceException;

    List<NewsDTO> findAll(int page, int size, String sortingField, String sortingType) throws ServiceException;

    List<NewsDTO> findAll();

    long countAllNews();

    NewsDTO findById(long id) throws ServiceException;

    List<NewsDTO> findByTagName(String tagName, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByTagName(String tagName);

    List<NewsDTO> findByTagId(long tagId, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByTagId(long tagId);

    List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByPartOfAuthorName(String partOfAuthorName);

    List<NewsDTO> findByAuthorId(long authorId, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByAuthorId(long authorId);

    List<NewsDTO> findByPartOfTitle(String partOfTitle, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByPartOfTitle(String partOfTitle);

    List<NewsDTO> findByPartOfContent(String partOfContent, int page, int size, String sortingField, String sortingType) throws ServiceException;

    long countAllNewsByPartOfContent(String partOfContent);

    Pagination<NewsDTO> getPagination(List<NewsDTO> elementsOnPage, long countAllElements, int page, int size);
}