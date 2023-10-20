package com.mjc.school.service.news.impl;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.NewsRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_AUTHORS_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_CONTENT;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
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
    private final AuthorRepository authorRepository;
    private final NewsConverter newsConverter;
    private final DateHandler dateHandler;
    private final PaginationService<NewsDTO> newsPagination;

    @Transactional
    @Override
    public boolean create(NewsDTO newsDTO) throws ServiceException {
        if (newsRepository.existsByTitle(newsDTO.getTitle())) {
            newsDTO.setCreated(dateHandler.getCurrentDate());
            newsDTO.setModified(dateHandler.getCurrentDate());
            News news = newsConverter.fromDTO(newsDTO);
            news.setAuthor(authorRepository.getById(newsDTO.getId()));
            newsRepository.save(news);
            return true;
        } else {
            log.log(WARN, "News with title '" + newsDTO.getTitle() + "' exists.");
            throw new ServiceException("news_dto.title.not_valid.exists_news_title");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(long newsId) {
        if (newsRepository.existsById(newsId)) {
            newsRepository.deleteById(newsId);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteByAuthorId(long authorId) throws ServiceException {
        if (authorRepository.existsById(authorId)) {
            newsRepository.findByAuthorId(authorId)
                    .forEach(news -> newsRepository.deleteById(news.getId()));
            return true;
        } else {
            log.log(WARN, "Not found authors with ID: " + authorId);
            throw new ServiceException(NO_AUTHORS_WITH_ID);
        }
    }

    @Transactional
    @Override
    public NewsDTO deleteAllTagsFromNews(long newsId) throws ServiceException {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isPresent()) {
            newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
            News news = optionalNews.get();
            news.setTags(new ArrayList<>());
            return newsConverter.toDTO(news);
        } else {
            log.log(WARN, "Not found news with ID: " + newsId);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Transactional
    @Override
    public NewsDTO update(NewsDTO newsDTO) throws ServiceException {
        Optional<News> optionalNews = newsRepository.findById(newsDTO.getId());
        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            if (news.getTitle().equals(newsDTO.getTitle())) {
                news.setContent(newsDTO.getContent());
                news.setAuthor(authorRepository.getById(newsDTO.getAuthorId()));
                news.setModified(dateHandler.getCurrentDate());
                newsRepository.update(news.getTitle(), news.getContent(),
                        news.getAuthor().getId(), news.getModified(), news.getId());
                return newsConverter.toDTO(news);
            } else {
                if (!newsRepository.existsByTitle(newsDTO.getTitle())) {
                    news.setTitle(newsDTO.getTitle());
                    news.setContent(newsDTO.getContent());
                    news.setAuthor(authorRepository.getById(newsDTO.getAuthorId()));
                    news.setModified(dateHandler.getCurrentDate());
                    newsRepository.update(news.getTitle(), news.getContent(),
                            news.getAuthor().getId(), news.getModified(), news.getId());
                    return newsConverter.toDTO(news);
                } else {
                    log.log(WARN, "News with title '" + newsDTO.getTitle() + "' exists.");
                    throw new ServiceException("news_dto.title.not_valid.exists_news_title");
                }
            }
        } else {
            log.log(WARN, "Not found object with this ID: " + newsDTO.getId());
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<NewsDTO> findAll(int page, int size) throws ServiceException {
        Page<News> newsPage = newsRepository.findAll(
                PageRequest.of(newsPagination.calcNumberFirstElement(page, size), size));
        if (!newsPage.isEmpty()) {
            return newsPage.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<NewsDTO> findAll() {
        return newsRepository.findAll()
                .stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public NewsDTO findById(long id) throws ServiceException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return newsConverter.toDTO(news.get());
        } else {
            log.log(WARN, "Not found news with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<NewsDTO> findByTagName(String tagName, int page, int size) throws ServiceException {
        List<News> newsList = newsRepository.findByTagName(tagName,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag name: " + tagName);
            throw new ServiceException(NO_NEWS_WITH_TAG_NAME);
        }
    }

    @Override
    public List<NewsDTO> findByTagName(String tagName) {
        return newsRepository.findByTagName(tagName)
                .stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public long countAllNewsByTagName(String tagName) {
        return newsRepository.countAllNewsByTagName(tagName);
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId, int page, int size) throws ServiceException {
        List<News> newsList = newsRepository.findByTagId(tagId,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag ID: " + tagId);
            throw new ServiceException(NO_NEWS_WITH_TAG_ID);
        }
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId) {
        return newsRepository.findByTagId(tagId).stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName,
                                                int page, int size) throws ServiceException {
        String patternPartOfAuthorName = "%" + partOfAuthorName + "%";
        List<News> newsList = newsRepository.findByPartOfAuthorName(
                patternPartOfAuthorName,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered part of author name: " + partOfAuthorName);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName) {
        String patternPartOfAuthorName = "%" + partOfAuthorName + "%";
        return newsRepository.findByPartOfAuthorName(patternPartOfAuthorName)
                .stream()
                .map(newsConverter::toDTO)
                .toList();

    }

    @Override
    public List<NewsDTO> findByAuthorId(long authorId,
                                        int page, int size) throws ServiceException {
        List<News> newsList = newsRepository.findByAuthorId(
                authorId,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered author id: " + authorId);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<NewsDTO> findByAuthorId(long authorId) {
        return newsRepository.findByAuthorId(authorId)
                .stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle,
                                           int page, int size) throws ServiceException {
        String patternPartOfTitle = "%" + partOfTitle + "%";
        List<News> newsList = newsRepository.findByPartOfTitle(
                patternPartOfTitle,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with this part of title: " + partOfTitle);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_TITLE);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle) {
        String patternPartOfTitle = "%" + partOfTitle + "%";
        return newsRepository.findByPartOfTitle(patternPartOfTitle)
                .stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent,
                                             int page, int size) throws ServiceException {
        String patternPartOfContent = "%" + partOfContent + "%";
        List<News> newsList = newsRepository.findByPartOfContent(
                patternPartOfContent,
                newsPagination.calcNumberFirstElement(page, size),
                size);
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with this part of content: " + partOfContent);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_CONTENT);
        }
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent) {
        String patternPartOfContent = "%" + partOfContent + "%";
        return newsRepository.findByPartOfContent(patternPartOfContent)
                .stream()
                .map(newsConverter::toDTO)
                .toList();
    }

    @Override
    public List<NewsDTO> sort(List<NewsDTO> newsList,
                              SortNewsComparator comparator) throws ServiceException {
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
    public Pagination<NewsDTO> getPagination(List<NewsDTO> elementsOnPage, long countAllElements,
                                             int page, int size) {
        return newsPagination.getPagination(elementsOnPage, countAllElements, page, size);
    }
}