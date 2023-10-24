package com.mjc.school.service.news.impl;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.SortType;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.service.SortType.getSortType;
import static com.mjc.school.service.news.sort.NewsSortField.getSortField;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

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
            log.log(WARN, "Not found author by ID: " + authorId);
            throw new ServiceException("service.exception.not_found_author_by_id");
        }
    }

    @Transactional
    @Override
    public NewsDTO deleteAllTagsFromNews(long newsId) throws ServiceException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + newsId);
            return new ServiceException("service.exception.not_found_news_by_id");
        });
        newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
        news.setTags(new ArrayList<>());
        return newsConverter.toDTO(news);
    }

    @Transactional
    @Override
    public NewsDTO update(NewsDTO newsDTO) throws ServiceException {
        News news = newsRepository.findById(newsDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + newsDTO.getId());
            return new ServiceException("service.exception.not_found_news_by_id");
        });
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
    }

    @Override
    public List<NewsDTO> findAll(int page, int size,
                                 String sortingField, String sortingType) throws ServiceException {
        Page<News> newsPage = newsRepository.findAll(PageRequest.of(
                newsPagination.calcNumberFirstElement(page, size),
                size,
                Sort.by(fromOptionalString(sortingType).orElse(DESC),
                        getSortField(sortingField))));
        if (!newsPage.isEmpty()) {
            return newsPage.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news");
            throw new ServiceException("service.exception.not_found_news");
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
        News news = newsRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + id);
            return new ServiceException("service.exception.not_found_news_by_id");
        });
        return newsConverter.toDTO(news);

    }

    @Override
    public List<NewsDTO> findByTagName(String tagName, int page, int size,
                                       String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByTagName(tagName,
                newsPagination.calcNumberFirstElement(page, size),
                size, getSortField(sortingField), getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag name: " + tagName);
            throw new ServiceException("service.exception.not_found_news_by_tag_name");
        }
    }

    @Override
    public long countAllNewsByTagName(String tagName) {
        return newsRepository.countAllNewsByTagName(tagName);
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId, int page, int size,
                                     String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByTagId(tagId,
                newsPagination.calcNumberFirstElement(page, size),
                size,
                getSortField(sortingField),
                getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag ID: " + tagId);
            throw new ServiceException("service.exception.not_found_news_by_tag_id");
        }
    }

    @Override
    public long countAllNewsByTagId(long tagId) {
        return newsRepository.countAllNewsByTagId(tagId);
    }

    @Override
    public List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName, int page, int size,
                                                String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByPartOfAuthorName(
                "%" + partOfAuthorName + "%",
                newsPagination.calcNumberFirstElement(page, size),
                size,
                getSortField(sortingField),
                getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered part of author name: " + partOfAuthorName);
            throw new ServiceException("service.exception.not_found_news_by_part_of_author_name");
        }
    }

    @Override
    public long countAllNewsByPartOfAuthorName(String partOfAuthorName) {
        return newsRepository.countAllNewsByPartOfAuthorName("%" + partOfAuthorName + "%");
    }

    @Override
    public List<NewsDTO> findByAuthorId(long authorId, int page, int size,
                                        String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByAuthorId(authorId,
                newsPagination.calcNumberFirstElement(page, size),
                size,
                getSortField(sortingField),
                getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news by author id: " + authorId);
            throw new ServiceException("service.exception.not_found_news_by_author_id");
        }
    }

    @Override
    public long countAllNewsByAuthorId(long authorId) {
        return newsRepository.countAllNewsByAuthorId(authorId);
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle, int page, int size,
                                           String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByPartOfTitle(
                "%" + partOfTitle + "%",
                newsPagination.calcNumberFirstElement(page, size),
                size,
                getSortField(sortingField),
                getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with this part of title: " + partOfTitle);
            throw new ServiceException("service.exception.not_found_news_by_part_of_title");
        }
    }

    @Override
    public long countAllNewsByPartOfTitle(String partOfTitle) {
        return newsRepository.countAllNewsByPartOfTitle("%" + partOfTitle + "%");
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent, int page, int size,
                                             String sortingField, String sortingType) throws ServiceException {
        List<News> newsList = newsRepository.findByPartOfContent(
                "%" + partOfContent + "%",
                newsPagination.calcNumberFirstElement(page, size),
                size,
                getSortField(sortingField),
                getSortType(sortingType));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news by part of content: " + partOfContent);
            throw new ServiceException("service.exception.not_found_news_by_part_of_content");
        }
    }

    @Override
    public long countAllNewsByPartOfContent(String partOfContent) {
        return newsRepository.countAllNewsByPartOfContent("%" + partOfContent + "%");
    }

    @Override
    public Pagination<NewsDTO> getPagination(List<NewsDTO> elementsOnPage, long countAllElements, int page, int size) {
        return newsPagination.getPagination(elementsOnPage, countAllElements, page, size);
    }
}