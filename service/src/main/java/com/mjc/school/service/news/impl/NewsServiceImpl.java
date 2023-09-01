package com.mjc.school.service.news.impl;

import com.mjc.school.entity.News;
import com.mjc.school.exception.SortException;
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
     */
    @Override
    public boolean create(News news) {
        return newsRepository.create(news);
    }

    /**
     * Delete news by id .
     *
     * @param newsId the news id
     * @return the boolean
     */
    @Override
    public boolean deleteById(long newsId) {
        newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
        commentRepository.deleteByNewsId(newsId);
        return newsRepository.deleteById(newsId);
    }

    /**
     * Delete news by author id.
     *
     * @param authorId the author id
     * @return the boolean
     */
    @Override
    public boolean deleteByAuthorId(long authorId) {
        newsRepository.findAllNews()
                .stream()
                .filter(news -> news.getAuthorId() == authorId)
                .forEach(news -> {
                    newsRepository.deleteByNewsIdFromTableNewsTags(news.getId());
                    commentRepository.deleteByNewsId(news.getId());
                    newsRepository.deleteByAuthorId(authorId);
                });
        return !newsRepository.findAllNews()
                .stream()
                .filter(news -> news.getAuthorId() == authorId)
                .toList()
                .isEmpty();
    }

    /**
     * Delete news by news id from table news tags.
     *
     * @param newsId the news id
     * @return the boolean
     */
    @Override
    public boolean deleteByNewsIdFromTableNewsTags(long newsId) {
        return newsRepository.deleteByNewsIdFromTableNewsTags(newsId);
    }

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     */
    @Override
    public boolean update(News news) {
        return newsRepository.update(news);
    }

    /**
     * Sort news list.
     *
     * @param newsList   the news list
     * @param comparator the comparator
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<News> sortNews(List<News> newsList, SortNewsComparator comparator)
            throws SortException {
        List<News> sortedNewsList;
        if (newsList != null) {
            if (comparator != null) {
                sortedNewsList = new LinkedList<>(newsList);
                sortedNewsList.sort(comparator);
            } else {
                throw new SortException("comparator is null");
            }
        } else {
            throw new SortException("newsList is null");
        }
        return sortedNewsList;
    }

    /**
     * Sort news by created date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<News> sortNewsByCreatedDateTimeAsc(List<News> newsList)
            throws SortException {
        return sortNews(newsList, new SortNewsComparatorByCreatedDateTimeAsc());
    }

    /**
     * Sort news by created date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<News> sortNewsByCreatedDateTimeDesc(List<News> newsList)
            throws SortException {
        return sortNews(newsList, new SortNewsComparatorByCreatedDateTimeDesc());
    }

    /**
     * Sort news by modified date time asc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<News> sortNewsByModifiedDateTimeAsc(List<News> newsList)
            throws SortException {
        return sortNews(newsList, new SortNewsComparatorByModifiedDateTimeAsc());
    }

    /**
     * Sort news by modified date time desc list.
     *
     * @param newsList the news list
     * @return the list
     * @throws SortException the sort exception
     */
    @Override
    public List<News> sortNewsByModifiedDateTimeDesc(List<News> newsList)
            throws SortException {
        return sortNews(newsList, new SortNewsComparatorByModifiedDateTimeDesc());
    }

    /**
     * Find all news list.
     *
     * @return the list
     */
    @Override
    public List<News> findAllNews() {
        List<News> newsList = newsRepository.findAllNews();
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     */
    @Override
    public News findNewsById(long newsId) {
        News news = newsRepository.findNewsById(newsId);
        if (news != null) {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        }
        return news;
    }

    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     */
    @Override
    public List<News> findNewsByTagName(String tagName) {
        List<News> newsList = newsRepository.findNewsByTagName(tagName);
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }

    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     */
    @Override
    public List<News> findNewsByTagId(long tagId) {
        List<News> newsList = newsRepository.findNewsByTagId(tagId);
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     */
    @Override
    public List<News> findNewsByAuthorName(String authorName) {
        List<News> newsList = newsRepository.findNewsByAuthorName(authorName);
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }

    /**
     * Find news by part of title list.
     *
     * @param partOfTitle the part of title
     * @return the list
     */
    @Override
    public List<News> findNewsByPartOfTitle(String partOfTitle) {
        Pattern p = Pattern.compile(partOfTitle);
        List<News> newsList = findAllNews()
                .stream()
                .filter(news ->
                        (p.matcher(news.getTitle()).find()) ||
                                (p.matcher(news.getTitle()).lookingAt()) ||
                                (news.getTitle().matches(partOfTitle))
                ).toList();
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }

    /**
     * Find news by part of content list.
     *
     * @param partOfContent the part of content
     * @return the list
     */
    @Override
    public List<News> findNewsByPartOfContent(String partOfContent) {
        Pattern p = Pattern.compile(partOfContent);
        List<News> newsList = findAllNews()
                .stream()
                .filter(news ->
                        (p.matcher(news.getContent()).find()) ||
                                (p.matcher(news.getContent()).lookingAt()) ||
                                (news.getContent().matches(partOfContent))
                ).toList();
        newsList.forEach(news -> {
            news.setComments(commentRepository.findCommentsByNewsId(news.getId()));
            news.setTags(tagRepository.findByNewsId(news.getId()));
        });
        return newsList;
    }
}