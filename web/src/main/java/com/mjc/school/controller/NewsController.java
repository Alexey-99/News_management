package com.mjc.school.controller;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortField;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_NEWS_TITLE;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;
import static com.mjc.school.name.SortField.MODIFIED;
import static com.mjc.school.name.SortType.ASCENDING;
import static com.mjc.school.name.SortType.DESCENDING;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v2/news")
@Api(value = "Operations for news in the application")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created a news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Create a news.
            Response: true - if successful created news, if didn't create news - false.
            """, response = Boolean.class)
    @PostMapping
    public ResponseEntity<Boolean> create(
            @Valid
            @RequestBody
            @NotNull
            NewsDTO newsDTO)
            throws ServiceException {
        boolean result = newsService.create(newsDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a news by id.
            Response: true - if successful deleted news, if didn't delete news - false.
            """, response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException {
        boolean result = newsService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a news by author id.
            Response: true - if successful deleted news, if didn't delete news - false.
            """, response = Boolean.class)
    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<Boolean> deleteByAuthorId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long authorId)
            throws ServiceException, IncorrectParameterException {
        boolean result = newsService.deleteByAuthorId(authorId);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted all tags from news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete all tags from news by news id.
            Response: true - if successful deleted all tags from news, if didn't delete all tags from news - false.
            """, response = Boolean.class)
    @DeleteMapping("/tags/{newsId}")
    public ResponseEntity<Boolean> deleteAllTagsFromNewsByNewsId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException {
        boolean result = newsService.deleteAllTagsFromNewsByNewsId(newsId);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful updated a news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Update a news.
            Response: true - if successful updated news, if didn't update news - false.
            """, response = Boolean.class)
    @PutMapping(value = "/{id}")
    public boolean update(@PathVariable
                          @Min(value = 1, message = BAD_ID)
                          long id,
                          @Valid
                          @RequestBody
                          @NotNull NewsDTO newsDTO)
            throws ServiceException {
        newsDTO.setId(id);
        return newsService.update(newsDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all news.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/all")
    public Pagination<NewsDTO> findAll(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findAll(page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by id.
            Response: news.
            """, response = NewsDTO.class)
    @GetMapping("/{id}")
    public NewsDTO findById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException {
        return newsService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by tag name.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/tag-name/{tagName}")
    public Pagination<NewsDTO> findNewsByTagName(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @Size(min = 3, max = 15, message = BAD_TAG_NAME)
            String tagName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findByTagName(tagName, page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by tag id.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/tag/{tagId}")
    public Pagination<NewsDTO> findNewsByTagId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long tagId,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findByTagId(tagId, page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by author name.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/author-name/{authorName}")
    public Pagination<NewsDTO> findNewsByAuthorName(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @Size(min = 3, max = 15, message = BAD_AUTHOR_NAME)
            String authorName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findByAuthorName(authorName, page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by part of title.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/part-title/{partOfTitle}")
    public Pagination<NewsDTO> findNewsByPartOfTitle(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_NEWS_TITLE)
            @NotBlank(message = BAD_PARAMETER_PART_OF_NEWS_TITLE)
            String partOfTitle,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findByPartOfTitle(partOfTitle, page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View news by part of content.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/part-content/{partOfContent}")
    public Pagination<NewsDTO> findNewsByPartOfContent(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_NEWS_CONTENT)
            @NotBlank(message = BAD_PARAMETER_PART_OF_NEWS_CONTENT)
            String partOfContent,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page)
            throws ServiceException {
        return newsService.getPagination(
                newsService.findByPartOfContent(partOfContent, page, size),
                page, size);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View sorted all news.
            Response: pagination of news.
            """, response = Pagination.class)
    @GetMapping("/sort")
    public Pagination<NewsDTO> sort(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            int size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            int page,
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
        if (sortingField != null &&
                sortingField.equals(SortField.CREATED)) {
            if (sortingType != null &&
                    sortingType.equals(ASCENDING)) {
                sortedList = newsService.getPagination(
                        newsService.sortByCreatedDateTimeAsc(
                                newsService.findAll(page, size)),
                        page, size);
            } else {
                sortedList = newsService.getPagination(
                        newsService.sortByCreatedDateTimeDesc(
                                newsService.findAll(page, size)),
                        page, size);
            }
        } else {
            if (sortingType != null &&
                    sortingType.equals(ASCENDING)) {
                sortedList = newsService.getPagination(
                        newsService.sortByModifiedDateTimeAsc(
                                newsService.findAll(page, size)),
                        page, size);
            } else {
                sortedList = newsService.getPagination(
                        newsService.sortByModifiedDateTimeDesc(
                                newsService.findAll(page, size)),
                        page, size);
            }
        }
        return sortedList;
    }
}