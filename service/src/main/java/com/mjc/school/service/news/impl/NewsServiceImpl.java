package com.mjc.school.service.news.impl;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.news.impl.sort.NewsSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.news.impl.sort.NewsSortField.MODIFIED;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@Log4j2
@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final NewsConverter newsConverter;
    private final DateHandler dateHandler;
    private final PaginationService paginationService;

    @Transactional
    @Override
    public boolean create(NewsDTO newsDTO) throws ServiceBadRequestParameterException {
        if (newsRepository.notExistsByTitle(newsDTO.getTitle())) {
            newsDTO.setCreated(dateHandler.getCurrentDate());
            newsDTO.setModified(dateHandler.getCurrentDate());
            News news = newsConverter.fromDTO(newsDTO);
            newsRepository.save(news);
            return true;
        } else {
            log.log(WARN, "News with title '" + newsDTO.getTitle() + "' exists.");
            throw new ServiceBadRequestParameterException("news_dto.title.not_valid.exists_news_title");
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
    public boolean deleteByAuthorId(long authorId) throws ServiceBadRequestParameterException {
        if (authorRepository.existsById(authorId)) {
            newsRepository.findByAuthorId(authorId)
                    .forEach(news -> newsRepository.deleteById(news.getId()));
            return true;
        } else {
            log.log(WARN, "Not found author by ID: " + authorId);
            throw new ServiceBadRequestParameterException("service.exception.not_found_author_by_id");
        }
    }

    @Transactional
    @Override
    public NewsDTO deleteAllTagsFromNews(long newsId) throws ServiceBadRequestParameterException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + newsId);
            return new ServiceBadRequestParameterException("service.exception.not_found_news_by_id");
        });
        newsRepository.deleteAllTagsFromNewsByNewsId(newsId);
        news.setTags(List.of());
        return newsConverter.toDTO(news);
    }

    @Transactional
    @Override
    public NewsDTO update(NewsDTO newsDTO) throws ServiceBadRequestParameterException {
        News news = newsRepository.findById(newsDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + newsDTO.getId());
            return new ServiceBadRequestParameterException("service.exception.not_found_news_by_id");
        });
        if (news.getTitle().equals(newsDTO.getTitle())) {
            news.setContent(newsDTO.getContent());
            news.setAuthor(authorRepository.findById(newsDTO.getAuthorId()).orElseThrow(() -> {
                log.log(WARN, "Not found author by ID: " + newsDTO.getAuthorId());
                return new ServiceBadRequestParameterException("service.exception.not_exists_author_by_id");
            }));
            news.setModified(dateHandler.getCurrentDate());
            newsRepository.update(news.getTitle(), news.getContent(),
                    news.getAuthor().getId(), news.getModified(), news.getId());
            return newsConverter.toDTO(news);
        } else {
            if (newsRepository.notExistsByTitle(newsDTO.getTitle())) {
                news.setTitle(newsDTO.getTitle());
                news.setContent(newsDTO.getContent());
                news.setAuthor(authorRepository.findById(newsDTO.getAuthorId()).orElseThrow(() -> {
                    log.log(WARN, "Not found author by ID: " + newsDTO.getAuthorId());
                    return new ServiceBadRequestParameterException("service.exception.not_exists_author_by_id");
                }));
                news.setModified(dateHandler.getCurrentDate());
                newsRepository.update(news.getTitle(), news.getContent(),
                        news.getAuthor().getId(), news.getModified(), news.getId());
                return newsConverter.toDTO(news);
            } else {
                log.log(WARN, "News with title '" + newsDTO.getTitle() + "' exists.");
                throw new ServiceBadRequestParameterException("news_dto.title.not_valid.exists_news_title");
            }
        }
    }

    @Override
    public List<NewsDTO> findAll(int page, int size,
                                 String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsPage = newsRepository.findAllList(PageRequest.of(
                paginationService.calcNumberFirstElement(page, size), size,
                Sort.by(fromOptionalString(sortingType).orElse(DESC),
                        getOptionalSortField(sortingField)
                                .orElse(MODIFIED).name().toLowerCase())));
        if (!newsPage.isEmpty()) {
            return newsPage.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news");
            throw new ServiceNoContentException();
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
    public NewsDTO findById(long id) throws ServiceNoContentException {
        News news = newsRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + id);
            return new ServiceNoContentException();
        });
        return newsConverter.toDTO(news);
    }

    @Override
    public List<NewsDTO> findByTagName(String tagName,
                                       int page, int size,
                                       String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByTagName(tagName,
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag name: " + tagName);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByTagName(String tagName) {
        return newsRepository.countAllNewsByTagName(tagName);
    }

    @Override
    public List<NewsDTO> findByTagId(long tagId,
                                     int page, int size,
                                     String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByTagId(tagId,
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered tag ID: " + tagId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByTagId(long tagId) {
        return newsRepository.countAllNewsByTagId(tagId);
    }

    @Override
    public List<NewsDTO> findByPartOfAuthorName(String partOfAuthorName,
                                                int page, int size,
                                                String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByPartOfAuthorName(
                "%" + partOfAuthorName + "%",
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with entered part of author name: " + partOfAuthorName);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByPartOfAuthorName(String partOfAuthorName) {
        return newsRepository.countAllNewsByPartOfAuthorName("%" + partOfAuthorName + "%");
    }

    @Override
    public List<NewsDTO> findByAuthorId(long authorId,
                                        int page, int size,
                                        String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByAuthorId(authorId,
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news by author id: " + authorId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByAuthorId(long authorId) {
        return newsRepository.countAllNewsByAuthorId(authorId);
    }

    @Override
    public List<NewsDTO> findByPartOfTitle(String partOfTitle,
                                           int page, int size,
                                           String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByPartOfTitle(
                "%" + partOfTitle + "%",
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news with this part of title: " + partOfTitle);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByPartOfTitle(String partOfTitle) {
        return newsRepository.countAllNewsByPartOfTitle("%" + partOfTitle + "%");
    }

    @Override
    public List<NewsDTO> findByPartOfContent(String partOfContent,
                                             int page, int size,
                                             String sortingField, String sortingType) throws ServiceNoContentException {
        List<News> newsList = newsRepository.findByPartOfContent(
                "%" + partOfContent + "%",
                PageRequest.of(paginationService.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getOptionalSortField(sortingField)
                                        .orElse(MODIFIED).name().toLowerCase())));
        if (!newsList.isEmpty()) {
            return newsList.stream()
                    .map(newsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found news by part of content: " + partOfContent);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllNewsByPartOfContent(String partOfContent) {
        return newsRepository.countAllNewsByPartOfContent("%" + partOfContent + "%");
    }

    @Override
    public Pagination<NewsDTO> getPagination(List<NewsDTO> elementsOnPage, long countAllElements, int page, int size) {
        return Pagination
                .<NewsDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .numberPage(page)
                .maxNumberPage(paginationService.calcMaxNumberPage(countAllElements, size))
                .build();
    }

    @Override
    public Optional<NewsSortField> getOptionalSortField(String sortField) {
        try {
            return sortField != null ?
                    Optional.of(NewsSortField.valueOf(sortField.toUpperCase())) :
                    Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}