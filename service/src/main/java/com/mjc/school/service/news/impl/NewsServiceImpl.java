package com.mjc.school.service.news.impl;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.entity.News;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeDesc;
import com.mjc.school.validation.dto.NewsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.INSERT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_AUTHORS_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_CONTENT;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_TITLE;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.UPDATE_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsConverter newsConverter;
    @Autowired
    private AuthorConverter authorConverter;
    @Autowired
    private CommentConverter commentConverter;
    @Autowired
    private TagConverter tagConverter;
    @Autowired
    private DateHandler dateHandler;
    @Autowired
    private PaginationService<NewsDTO> newsPagination;

    @Override
    public boolean create(NewsDTO newsDTO)
            throws ServiceException {
        try {
            if ((authorRepository.findById(newsDTO.getAuthorId()) != null)) {
                newsDTO.setCreated(dateHandler.getCurrentDate());
                newsDTO.setModified(dateHandler.getCurrentDate());
                return newsRepository.create(
                        newsConverter.fromDTO(newsDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(INSERT_ERROR);
        }
    }

    @Override
    public boolean deleteById(long newsId)
            throws ServiceException {
        try {
            newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
            commentRepository.deleteByNewsId(newsId);
            newsRepository.deleteById(newsId);
            return newsRepository.findById(newsId) == null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean deleteByAuthorId(long authorId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (authorRepository.findById(authorId) != null) {
                for (News news : newsRepository.findAll()
                        .stream()
                        .filter(news -> news.getAuthor().getId() == authorId)
                        .toList()) {
                    newsRepository.deleteAllTagsFromNewsByNewsId(news.getId());
                    commentRepository.deleteByNewsId(news.getId());
                    newsRepository.deleteByAuthorId(authorId);
                }
                return newsRepository.findAll()
                        .stream()
                        .filter(news -> news.getAuthor().getId() == authorId)
                        .toList()
                        .isEmpty();
            } else {
                log.log(WARN, "Not found authors with ID: " + authorId);
                throw new IncorrectParameterException(NO_AUTHORS_WITH_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long newsId)
            throws ServiceException {
        try {
            newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
            return findAll()
                    .stream()
                    .filter(news -> !news.getTags()
                            .isEmpty())
                    .toList()
                    .isEmpty();
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean update(NewsDTO newsDTO)
            throws ServiceException {
        try {
            if ((authorRepository.findById(newsDTO.getAuthorId()) != null)) {
                newsDTO.setModified(dateHandler.getCurrentDate());
                return newsRepository.update(
                        newsConverter.fromDTO(newsDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(UPDATE_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findAll() throws ServiceException {
        try {
            List<News> newsList = newsRepository.findAll();
            if (!newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setAuthor(authorRepository.findById(news.getAuthor().getId()));
                    news.setComments(commentRepository.findByNewsId(news.getId()));
                    news.setTags(tagRepository.findByNewsId(news.getId()));
                }
                return newsList.stream()
                        .map(news -> newsConverter.toDTO(news))
                        .toList();
            } else {
                log.log(WARN, "Not found news");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public NewsDTO findById(long id) throws ServiceException {
        try {
            News news = newsRepository.findById(id);
            if (news != null) {
                news.setAuthor(
                        authorRepository.findById(
                                news.getAuthor().getId()));
                news.setComments(
                        commentRepository.findByNewsId(news.getId()));
                news.setTags(
                        tagRepository.findByNewsId(news.getId()));
                return newsConverter.toDTO(news);
            } else {
                log.log(WARN, "Not found news with this ID: " + id);
                throw new ServiceException(NO_ENTITY_WITH_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findByTagName(String tagName)
            throws ServiceException {
        try {
            List<News> newsList = newsRepository.findByTagName(tagName);
            if (!newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setAuthor(
                            authorRepository.findById(
                                    news.getAuthor().getId()));
                    news.setComments(
                            commentRepository.findByNewsId(news.getId()));
                    news.setTags(
                            tagRepository.findByNewsId(news.getId()));
                }
                return newsList.stream()
                        .map(news -> newsConverter.toDTO(news))
                        .toList();
            } else {
                log.log(WARN, "Not found news with entered tag name: " + tagName);
                throw new ServiceException(NO_NEWS_WITH_TAG_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId)
            throws ServiceException {
        try {
            List<News> newsList = newsRepository.findByTagId(tagId);
            if (!newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setAuthor(
                            authorRepository.findById(news.getAuthor().getId()));
                    news.setComments(
                            commentRepository.findByNewsId(news.getId()));
                    news.setTags(
                            tagRepository.findByNewsId(news.getId()));
                }
                return newsList.stream()
                        .map(news -> newsConverter.toDTO(news))
                        .toList();
            } else {
                log.log(WARN, "Not found news with entered tag ID: " + tagId);
                throw new ServiceException(NO_NEWS_WITH_TAG_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findByAuthorName(String authorName)
            throws ServiceException {
        try {
            List<News> newsList = newsRepository.findByAuthorName(authorName);
            if (!newsList.isEmpty()) {
                for (News news : newsList) {
                    news.setAuthor(
                            authorRepository.findById(news.getAuthor().getId()));
                    news.setComments(
                            commentRepository.findByNewsId(news.getId()));
                    news.setTags(
                            tagRepository.findByNewsId(news.getId()));
                }
                return newsList.stream()
                        .map(news -> newsConverter.toDTO(news))
                        .toList();
            } else {
                log.log(WARN, "Not found news with entered author name: " + authorName);
                throw new ServiceException(BAD_PARAMETER_PART_OF_AUTHOR_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle)
            throws ServiceException {
        try {
            String pattern = partOfTitle.toLowerCase();
            Pattern p = Pattern.compile(pattern);
            List<NewsDTO> newsList = findAll()
                    .stream()
                    .filter(news -> {
                        String newsTitle = news.getTitle().toLowerCase();
                        return (p.matcher(newsTitle).find()) ||
                                (p.matcher(newsTitle).lookingAt()) ||
                                (newsTitle.matches(pattern));
                    }).toList();
            if (!newsList.isEmpty()) {
                for (NewsDTO newsDTO : newsList) {
                    newsDTO.setAuthor(
                            authorConverter.toDTO(
                                    authorRepository.findById(
                                            newsDTO.getAuthor().getId())));
                    newsDTO.setComments(
                            commentRepository.findByNewsId(
                                            newsDTO.getId())
                                    .stream()
                                    .map(comment ->
                                            commentConverter.toDTO(comment))
                                    .toList());
                    newsDTO.setTags(
                            tagRepository.findByNewsId(
                                            newsDTO.getId())
                                    .stream()
                                    .map(tag -> tagConverter.toDTO(tag))
                                    .toList());
                }
                return newsList;
            } else {
                log.log(WARN, "Not found news with this part of title: " + partOfTitle);
                throw new ServiceException(NO_ENTITY_WITH_PART_OF_TITLE);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent)
            throws ServiceException {
        try {
            String pattern = partOfContent.toLowerCase();
            Pattern p = Pattern.compile(pattern);
            List<NewsDTO> newsList = findAll()
                    .stream()
                    .filter(news -> {
                        String newsContent = news.getTitle().toLowerCase();
                        return (p.matcher(newsContent).find()) ||
                                (p.matcher(newsContent).lookingAt()) ||
                                (newsContent.matches(pattern));
                    }).toList();
            if (!newsList.isEmpty()) {
                for (NewsDTO newsDTO : newsList) {
                    newsDTO.setAuthor(
                            authorConverter.toDTO(
                                    authorRepository.findById(
                                            newsDTO.getAuthor().getId())));
                    newsDTO.setComments(
                            commentRepository.findByNewsId(
                                            newsDTO.getId())
                                    .stream()
                                    .map(comment ->
                                            commentConverter.toDTO(comment))
                                    .toList());
                    newsDTO.setTags(
                            tagRepository.findByNewsId(
                                            newsDTO.getId())
                                    .stream()
                                    .map(tag -> tagConverter.toDTO(tag))
                                    .toList());
                }
                return newsList;
            } else {
                log.log(WARN, "Not found news with this part of content: " + partOfContent);
                throw new ServiceException(NO_ENTITY_WITH_PART_OF_CONTENT);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<NewsDTO> sort(List<NewsDTO> newsList, SortNewsComparator comparator)
            throws ServiceException {
        List<NewsDTO> sortedNewsList;
        if (newsList != null) {
            if (comparator != null) {
                sortedNewsList = new LinkedList<>(newsList);
                sortedNewsList.sort(comparator);
            } else {
                log.log(ERROR, "comparator is null");
                throw new ServiceException(SORT_ERROR);
            }
        } else {
            log.log(ERROR, "list is null");
            throw new ServiceException(SORT_ERROR);
        }
        return sortedNewsList;
    }

    @Override
    public List<NewsDTO> sortByCreatedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByCreatedDateTimeAsc());
    }

    @Override
    public List<NewsDTO> sortByCreatedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByCreatedDateTimeDesc());
    }

    @Override
    public List<NewsDTO> sortByModifiedDateTimeAsc(List<NewsDTO> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByModifiedDateTimeAsc());
    }

    @Override
    public List<NewsDTO> sortByModifiedDateTimeDesc(List<NewsDTO> newsList)
            throws ServiceException {
        return sort(newsList, new SortNewsComparatorByModifiedDateTimeDesc());
    }

    @Override
    public Pagination<NewsDTO> getPagination(List<NewsDTO> list, long size,
                                             long page) {
        return newsPagination.getPagination(list, size, page);
    }
}