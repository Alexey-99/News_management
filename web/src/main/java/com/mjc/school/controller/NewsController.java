package com.mjc.school.controller;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortField;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/news")
@Api(value = "Operations for news in the application")
public class NewsController {
    private final NewsService newsService;

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
    public ResponseEntity<Boolean> create(@Valid
                                          @RequestBody
                                          @NotNull
                                          NewsDTO newsDTO) {
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
    public ResponseEntity<Boolean> deleteById(@PathVariable
                                              @Min(value = 1, message = BAD_ID)
                                              long id) {
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
    public ResponseEntity<Boolean> deleteByAuthorId(@PathVariable
                                                    @Min(value = 1, message = BAD_ID)
                                                    long authorId)
            throws ServiceException {
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
    public ResponseEntity<Boolean> deleteAllTagsFromNewsByNewsId(@PathVariable
                                                                 @Min(value = 1, message = BAD_ID)
                                                                 long newsId) {
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
    public ResponseEntity<NewsDTO> update(@PathVariable
                                          @Min(value = 1, message = BAD_ID)
                                          long id,
                                          @Valid
                                          @RequestBody
                                          @NotNull
                                          NewsDTO newsDTO)
            throws ServiceException {
        newsDTO.setId(id);
        return new ResponseEntity<>(newsService.update(newsDTO), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findAll(@RequestAttribute(value = "size")
                                                       int size,
                                                       @RequestAttribute(value = "page")
                                                       int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findAll(page, size),
                        newsService.findAll(),
                        page, size), OK);
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
    public ResponseEntity<NewsDTO> findById(@PathVariable
                                            @Min(value = 1, message = BAD_ID)
                                            long id)
            throws ServiceException {
        return new ResponseEntity<>(newsService.findById(id), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findNewsByTagName(@PathVariable
                                                                 @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
                                                                 @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
                                                                 @Size(min = 3, max = 15, message = BAD_TAG_NAME)
                                                                 String tagName,
                                                                 @RequestAttribute(value = "size")
                                                                 int size,
                                                                 @RequestAttribute(value = "page")
                                                                 int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findByTagName(tagName, page, size),
                        newsService.findByTagName(tagName),
                        page, size), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findNewsByTagId(@PathVariable
                                                               @Min(value = 1, message = BAD_ID)
                                                               long tagId,
                                                               @RequestAttribute(value = "size")
                                                               int size,
                                                               @RequestAttribute(value = "page")
                                                               int page)
            throws ServiceException {
        return new ResponseEntity<>(newsService.getPagination(
                newsService.findByTagId(tagId, page, size),
                newsService.findByTagId(tagId),
                page, size), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findNewsByAuthorName(@PathVariable
                                                                    @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
                                                                    @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
                                                                    @Size(min = 3, max = 15, message = BAD_AUTHOR_NAME)
                                                                    String authorName,
                                                                    @RequestAttribute(value = "size")
                                                                    int size,
                                                                    @RequestAttribute(value = "page")
                                                                    int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findByPartOfAuthorName(authorName, page, size),
                        newsService.findByPartOfAuthorName(authorName),
                        page, size), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findNewsByPartOfTitle(@PathVariable
                                                                     @NotNull(message = BAD_PARAMETER_PART_OF_NEWS_TITLE)
                                                                     @NotBlank(message = BAD_PARAMETER_PART_OF_NEWS_TITLE)
                                                                     String partOfTitle,
                                                                     @RequestAttribute(value = "size")
                                                                     int size,
                                                                     @RequestAttribute(value = "page")
                                                                     int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findByPartOfTitle(partOfTitle, page, size),
                        newsService.findByPartOfTitle(partOfTitle),
                        page, size), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> findNewsByPartOfContent(@PathVariable
                                                                       @NotNull(message = BAD_PARAMETER_PART_OF_NEWS_CONTENT)
                                                                       @NotBlank(message = BAD_PARAMETER_PART_OF_NEWS_CONTENT)
                                                                       String partOfContent,
                                                                       @RequestAttribute(value = "size")
                                                                       int size,
                                                                       @RequestAttribute(value = "page")
                                                                       int page)
            throws ServiceException {
        return new ResponseEntity<>(newsService.getPagination(
                newsService.findByPartOfContent(partOfContent, page, size),
                newsService.findByPartOfContent(partOfContent),
                page, size), OK);
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
    public ResponseEntity<Pagination<NewsDTO>> sort(@RequestAttribute(value = "size")
                                                    int size,
                                                    @RequestAttribute(value = "page")
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
                        newsService.sortByCreatedDateTimeAsc(
                                newsService.findAll()),
                        page, size);
            } else {
                sortedList = newsService.getPagination(
                        newsService.sortByCreatedDateTimeDesc(
                                newsService.findAll(page, size)),
                        newsService.sortByCreatedDateTimeDesc(
                                newsService.findAll()),
                        page, size);
            }
        } else {
            if (sortingType != null &&
                    sortingType.equals(ASCENDING)) {
                sortedList = newsService.getPagination(
                        newsService.sortByModifiedDateTimeAsc(
                                newsService.findAll(page, size)),
                        newsService.sortByModifiedDateTimeAsc(
                                newsService.findAll()),
                        page, size);
            } else {
                sortedList = newsService.getPagination(
                        newsService.sortByModifiedDateTimeDesc(
                                newsService.findAll(page, size)),
                        newsService.sortByModifiedDateTimeDesc(
                                newsService.findAll()),
                        page, size);
            }
        }
        return new ResponseEntity<>(sortedList, OK);
    }
}