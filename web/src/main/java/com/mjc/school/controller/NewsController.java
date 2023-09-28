package com.mjc.school.controller;

import com.mjc.school.entity.News;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortingField;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mjc.school.name.SortingField.MODIFIED;
import static com.mjc.school.name.SortingType.ASCENDING;
import static com.mjc.school.name.SortingType.DESCENDING;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody NewsDTO newsDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.create(newsDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<Boolean> deleteByAuthorId(@PathVariable long authorId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteByAuthorId(authorId);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/tags/news/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsIdFromTableNewsTags(
            @PathVariable long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteAllTagsFromNewsByNewsId(newsId);
        return new ResponseEntity<>(result, OK);
    }

    @PutMapping
    public boolean update(@RequestBody NewsDTO newsDTO)
            throws ServiceException, IncorrectParameterException {
        return newsService.update(newsDTO);
    }

    @GetMapping("/all")
    public Pagination<NewsDTO> findAllNews(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findAll(),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/id/{id}")
    public NewsDTO findNewsById(@PathVariable long id)
            throws ServiceException, IncorrectParameterException {
        return newsService.findById(id);
    }

    @GetMapping("/tag-name/{tagName}")
    public Pagination<NewsDTO> findNewsByTagName(
            @PathVariable String tagName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagName(tagName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/tag-id/{tagId}")
    public Pagination<NewsDTO> findNewsByTagId(
            @PathVariable long tagId,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagId(tagId),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/author-name/{authorName}")
    public Pagination<NewsDTO> findNewsByAuthorName(
            @PathVariable String authorName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByAuthorName(authorName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/part-title/{partOfTitle}")
    public Pagination<NewsDTO> findNewsByPartOfTitle(
            @PathVariable String partOfTitle,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByPartOfTitle(partOfTitle),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/part-content/{partOfContent}")
    public Pagination<NewsDTO> findNewsByPartOfContent(
            @PathVariable String partOfContent,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByPartOfContent(partOfContent),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/sort")
    public Pagination<NewsDTO> sort(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "5")
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = "1")
            long numberPage,
            @RequestParam(value = "field",
                    required = false,
                    defaultValue = MODIFIED)
            String sortingField,
            @RequestParam(value = "type",
                    required = false,
                    defaultValue = DESCENDING)
            String sortingType)
            throws ServiceException {
        Pagination<NewsDTO> sortedList = null;
        switch (sortingField) {
            case SortingField.CREATED -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = newsService.getPagination(
                            newsService.sortByCreatedDateTimeAsc(
                                    newsService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = newsService.getPagination(
                            newsService.sortByCreatedDateTimeDesc(
                                    newsService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
            default -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = newsService.getPagination(
                            newsService.sortByModifiedDateTimeAsc(
                                    newsService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = newsService.getPagination(
                            newsService.sortByModifiedDateTimeDesc(
                                    newsService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
        }
        return sortedList;
    }
}