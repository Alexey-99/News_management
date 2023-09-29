package com.mjc.school.controller;

import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortField;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static com.mjc.school.name.SortField.MODIFIED;
import static com.mjc.school.name.SortType.ASCENDING;
import static com.mjc.school.name.SortType.DESCENDING;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
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
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<Boolean> deleteByAuthorId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long authorId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteByAuthorId(authorId);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/tags/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsIdFromTableNewsTags(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId)
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
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findAll(),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/{id}")
    public NewsDTO findNewsById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        return newsService.findById(id);
    }

    @GetMapping("/tag-name/{tagName}")
    public Pagination<NewsDTO> findNewsByTagName(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            String tagName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagName(tagName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/tag/{tagId}")
    public Pagination<NewsDTO> findNewsByTagId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long tagId,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByTagId(tagId),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/author-name/{authorName}")
    public Pagination<NewsDTO> findNewsByAuthorName(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            String authorName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByAuthorName(authorName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/part-title/{partOfTitle}")
    public Pagination<NewsDTO> findNewsByPartOfTitle(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            String partOfTitle,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return newsService.getPagination(
                newsService.findByPartOfTitle(partOfTitle),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/part-content/{partOfContent}")
    public Pagination<NewsDTO> findNewsByPartOfContent(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            String partOfContent,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
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
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
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
            case SortField.CREATED -> {
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