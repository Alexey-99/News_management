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
    boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException;

    boolean deleteAllTagsFromNewsByNewsId(long newsId)
            throws ServiceException;

    List<NewsDTO> findByTagName(String tagName, int page, int size)
            throws ServiceException;

    List<NewsDTO> findByTagId(long tagId, int page, int size)
            throws ServiceException;

    List<NewsDTO> findByAuthorName(String authorName, int page, int size)
            throws ServiceException;

    List<NewsDTO> findByPartOfTitle(String partOfTitle, int page, int size)
            throws ServiceException;

    List<NewsDTO> findByPartOfContent(String partOfContent, int page, int size)
            throws ServiceException;

    List<NewsDTO> sort(List<NewsDTO> newsList,
                       SortNewsComparator comparator)
            throws ServiceException;

    List<NewsDTO> sortByCreatedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException;

    List<NewsDTO> sortByCreatedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException;

    List<NewsDTO> sortByModifiedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException;

    List<NewsDTO> sortByModifiedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException;
}