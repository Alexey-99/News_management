package com.mjc.school.service.news.impl;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.News;
import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.impl.comment.CommentRepository;
import com.mjc.school.repository.impl.news.NewsRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.created.SortNewsComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeAsc;
import com.mjc.school.service.news.impl.comparator.impl.modified.SortNewsComparatorByModifiedDateTimeDesc;
import com.mjc.school.validation.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_AUTHORS_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_CONTENT;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_TITLE;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_NEWS_WITH_TAG_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LogManager.getLogger();
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final NewsConverter newsConverter;
    private final DateHandler dateHandler;
    private final PaginationService<NewsDTO> newsPagination;

    @Transactional
    @Override
    public boolean create(NewsDTO newsDTO) {
        Optional<Author> author = authorRepository.findById(newsDTO.getAuthorId());
        if (author.isPresent()) {
            newsDTO.setCreated(dateHandler.getCurrentDate());
            newsDTO.setModified(dateHandler.getCurrentDate());
            News news = newsConverter.fromDTO(newsDTO);
            news.setAuthor(author.get());
            newsRepository.save(news);
            return newsRepository.existsById(news.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(long newsId) {
        //newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
        commentRepository.deleteByNewsId(newsId);
        newsRepository.deleteById(newsId);
        return newsRepository.findById(newsId) == null;
    }

    @Override
    public boolean deleteByAuthorId(long authorId)
            throws IncorrectParameterException {
        if (authorRepository.findById(authorId) != null) {
            for (News news : newsRepository.findAll()
                    .stream()
                    .filter(news -> news.getAuthor().getId() == authorId)
                    .toList()) {
                //newsRepository.deleteAllTagsFromNewsByNewsId(news.getId());
                commentRepository.deleteByNewsId(news.getId());
                //newsRepository.deleteByAuthorId(authorId);
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
    }

    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long newsId)
            throws ServiceException {
        //newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
        return newsRepository.findAll()
                .stream()
                .filter(news -> !news.getTags().isEmpty())
                .toList()
                .isEmpty();
    }

    @Override
    public NewsDTO update(NewsDTO newsDTO)
            throws ServiceException {
        if ((authorRepository.findById(newsDTO.getAuthorId()) != null)) {
            newsDTO.setModified(dateHandler.getCurrentDate());
            return null;
//            newsConverter.toDTO(newsRepository.update(
//                    newsConverter.fromDTO(newsDTO)));
        } else {
            return null;
        }
    }


    @Override
    public List<NewsDTO> findAll(int page, int size) throws ServiceException {
        List<News> newsList = newsRepository.findAll();
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(news -> newsConverter.toDTO(news))
                    .toList();
        } else {
            log.log(WARN, "Not found news");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public NewsDTO findById(long id) throws ServiceException {
        //News news = newsRepository.findById(id);
//        if (news != null) {
//            return newsConverter.toDTO(news);
//        } else {
//            log.log(WARN, "Not found news with this ID: " + id);
//            throw new ServiceException(NO_ENTITY_WITH_ID);
//        }
        return null;
    }

    @Override
    public List<NewsDTO> findByTagName(String tagName, int page, int size)
            throws ServiceException {
//        List<News> newsList = newsRepository.findByTagName(tagName, page, size);
//        if (!newsList.isEmpty()) {
//            return newsList.stream()
//                    .map(news -> newsConverter.toDTO(news))
//                    .toList();
//        } else {
//            log.log(WARN, "Not found news with entered tag name: " + tagName);
//            throw new ServiceException(NO_NEWS_WITH_TAG_NAME);
//        }
        return null;
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId, int page, int size)
            throws ServiceException {
//        List<News> newsList = newsRepository.findByTagId(tagId, page, size);
//        if (!newsList.isEmpty()) {
//            return newsList.stream()
//                    .map(news -> newsConverter.toDTO(news))
//                    .toList();
//        } else {
//            log.log(WARN, "Not found news with entered tag ID: " + tagId);
//            throw new ServiceException(NO_NEWS_WITH_TAG_ID);
//        }
        return null;
    }

    @Override
    public List<NewsDTO> findByAuthorName(String authorName, int page, int size)
            throws ServiceException {
//        List<News> newsList = newsRepository.findByAuthorName(authorName, page, size);
//        if (!newsList.isEmpty()) {
//            return newsList.stream()
//                    .map(news -> newsConverter.toDTO(news))
//                    .toList();
//        } else {
//            log.log(WARN, "Not found news with entered author name: " + authorName);
//            throw new ServiceException(BAD_PARAMETER_PART_OF_AUTHOR_NAME);
//        }
        return null;
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle, int page, int size)
            throws ServiceException {
        String pattern = partOfTitle.toLowerCase();
        Pattern p = Pattern.compile(pattern);
        List<NewsDTO> newsList = findAll(page, size)
                .stream()
                .filter(news -> {
                    String newsTitle = news.getTitle().toLowerCase();
                    return (p.matcher(newsTitle).find()) ||
                            (p.matcher(newsTitle).lookingAt()) ||
                            (newsTitle.matches(pattern));
                }).toList();
        if (!newsList.isEmpty()) {
            return newsList;
        } else {
            log.log(WARN, "Not found news with this part of title: " + partOfTitle);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_TITLE);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent, int page, int size)
            throws ServiceException {
        String pattern = partOfContent.toLowerCase();
        Pattern p = Pattern.compile(pattern);
        List<NewsDTO> newsList = findAll(page, size)
                .stream()
                .filter(news -> {
                    String newsContent = news.getTitle().toLowerCase();
                    return (p.matcher(newsContent).find()) ||
                            (p.matcher(newsContent).lookingAt()) ||
                            (newsContent.matches(pattern));
                }).toList();
        if (!newsList.isEmpty()) {
            return newsList;
        } else {
            log.log(WARN, "Not found news with this part of content: " + partOfContent);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_CONTENT);
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
    public Pagination<NewsDTO> getPagination(List<NewsDTO> list,
                                             int size, int page) {
        return newsPagination.getPagination(list, size, page);
    }
}