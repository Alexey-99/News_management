package com.mjc.school.service.news.impl;

import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type News service.
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;

    /**
     * Create news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean create(News news) throws ServiceException {
        try {
            return newsRepository.create(news);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean deleteById(long newsId) throws ServiceException {
        try {
            newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
            commentRepository.deleteByNewsId(newsId);
            return newsRepository.deleteById(newsId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean deleteByAuthorId(long authorId) throws ServiceException {
        try {
            for (News news : newsRepository.findAllNews()
                    .stream()
                    .filter(news -> news.getAuthorId() == authorId).toList()) {
                newsRepository.deleteByNewsIdFromTableNewsTags(news.getId());
                commentRepository.deleteByNewsId(news.getId());
                newsRepository.deleteByAuthorId(authorId);
            }
            return !newsRepository.findAllNews()
                    .stream()
                    .filter(news -> news.getAuthorId() == authorId)
                    .toList()
                    .isEmpty();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete by news id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean deleteByNewsIdFromTableNewsTags(long newsId) throws ServiceException {
        try {
            return newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean update(News news) throws ServiceException {
        try {
            return newsRepository.update(news);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortNews(List<News> newsList, SortNewsComparator comparator)
            throws ServiceException {
        List<News> sortedNewsList;
        if (newsList != null) {
            if (comparator != null) {
                sortedNewsList = new LinkedList<>(newsList);
                sortedNewsList.sort(comparator);
            } else {
                throw new ServiceException("comparator is null");
            }
        } else {
            throw new ServiceException("newsList is null");
        }
        return sortedNewsList;
    }

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortNewsByCreatedDateTimeAsc(List<News> newsList)
            throws ServiceException {
        return sortNews(newsList, new SortNewsComparatorByCreatedDateTimeAsc());
    }

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortNewsByCreatedDateTimeDesc(List<News> newsList)
            throws ServiceException {
        return sortNews(newsList, new SortNewsComparatorByCreatedDateTimeDesc());
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortNewsByModifiedDateTimeAsc(List<News> newsList)
            throws ServiceException {
        return sortNews(newsList, new SortNewsComparatorByModifiedDateTimeAsc());
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> sortNewsByModifiedDateTimeDesc(List<News> newsList)
            throws ServiceException {
        return sortNews(newsList, new SortNewsComparatorByModifiedDateTimeDesc());
    }

    /**
     * Find all news list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findAllNews() throws ServiceException {
        try {
            List<News> newsList = newsRepository.findAllNews();
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws ServiceException the service exception
     */
    @Override
    public News findNewsById(long newsId) throws ServiceException {
        try {
            News news = newsRepository.findNewsById(newsId);
            if (news != null) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return news;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findNewsByTagName(String tagName) throws ServiceException {
        try {
            List<News> newsList = newsRepository.findNewsByTagName(tagName);
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findNewsByTagId(long tagId) throws ServiceException {
        try {
            List<News> newsList = newsRepository.findNewsByTagId(tagId);
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findNewsByAuthorName(String authorName) throws ServiceException {
        try {
            List<News> newsList = newsRepository.findNewsByAuthorName(authorName);
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findNewsByPartOfTitle(String partOfTitle) throws ServiceException {
        try {
            Pattern p = Pattern.compile(partOfTitle);
            List<News> newsList = findAllNews()
                    .stream()
                    .filter(news ->
                            (p.matcher(news.getTitle()).find()) ||
                                    (p.matcher(news.getTitle()).lookingAt()) ||
                                    (news.getTitle().matches(partOfTitle))
                    ).toList();
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<News> findNewsByPartOfContent(String partOfContent) throws ServiceException {
        try {
            Pattern p = Pattern.compile(partOfContent);
            List<News> newsList = findAllNews()
                    .stream()
                    .filter(news ->
                            (p.matcher(news.getContent()).find()) ||
                                    (p.matcher(news.getContent()).lookingAt()) ||
                                    (news.getContent().matches(partOfContent))
                    ).toList();
            for (News news : newsList) {
                news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
                news.setTags(tagRepository.findByNewsId(news.getId()));
            }
            return newsList;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}