package com.mjc.school.service.news;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.CRUDOperationService;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.validation.dto.NewsDTO;

import java.util.List;

public interface NewsService
        extends PaginationService<NewsDTO>,
        CRUDOperationService<NewsDTO> {
    public boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException;

    public boolean deleteAllTagsFromNewsByNewsId(long newsId)
            throws ServiceException;

    public List<NewsDTO> findByTagName(String tagName)
            throws ServiceException;

    public List<NewsDTO> findByTagId(long tagId)
            throws ServiceException;

    public List<NewsDTO> findByAuthorName(String authorName)
            throws ServiceException;

    public List<NewsDTO> findByPartOfTitle(String partOfTitle)
            throws ServiceException;

    public List<NewsDTO> findByPartOfContent(String partOfContent)
            throws ServiceException;

    public List<NewsDTO> sort(List<NewsDTO> newsList,
                              SortNewsComparator comparator)
            throws ServiceException;

    public List<NewsDTO> sortByCreatedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException;

    public List<NewsDTO> sortByCreatedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException;

    public List<NewsDTO> sortByModifiedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException;

    public List<NewsDTO> sortByModifiedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException;
}