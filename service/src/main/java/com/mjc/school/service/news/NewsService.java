package com.mjc.school.service.news;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.validation.dto.NewsDTO;

import java.util.List;

public interface NewsService extends BaseService<NewsDTO> {
    boolean deleteByAuthorId(long authorId)
            throws ServiceException;

    boolean deleteAllTagsFromNewsByNewsId(long newsId);

    List<NewsDTO> findByTagName(String tagName,
                                int page, int size)
            throws ServiceException;

    public List<NewsDTO> findByTagName(String tagName);

    List<NewsDTO> findByTagId(long tagId,
                              int page, int size)
            throws ServiceException;

    List<NewsDTO> findByTagId(long tagId);

    List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName,
                                         int page, int size)
            throws ServiceException;

    List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName);

    List<NewsDTO> findByAuthorId(long authorId,
                                 int page, int size)
            throws ServiceException;

    List<NewsDTO> findByAuthorId(long authorId);

    List<NewsDTO> findByPartOfTitle(String partOfTitle,
                                    int page, int size)
            throws ServiceException;

    List<NewsDTO> findByPartOfTitle(String partOfTitle);

    List<NewsDTO> findByPartOfContent(String partOfContent,
                                      int page, int size)
            throws ServiceException;

    List<NewsDTO> findByPartOfContent(String partOfContent);

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