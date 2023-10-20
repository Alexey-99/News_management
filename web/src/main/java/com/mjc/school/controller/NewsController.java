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

import static com.mjc.school.name.SortField.MODIFIED;
import static com.mjc.school.name.SortType.ASCENDING;
import static com.mjc.school.name.SortType.DESCENDING;
import static org.springframework.http.HttpStatus.CREATED;
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
                                          @NotNull(message = "news_controller.request_body.news_dto.in_valid.null")
                                          NewsDTO newsDTO) throws ServiceException {
        boolean result = newsService.create(newsDTO);
        return new ResponseEntity<>(result, CREATED);
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
                                              @Min(value = 1,
                                                      message = "news_controller.path_variable.id.in_valid.min")
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
                                                    @Min(value = 1,
                                                            message = "news_controller.path_variable.id.in_valid.min")
                                                    long authorId) throws ServiceException {
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
    @DeleteMapping("/all-tags/{newsId}")
    public ResponseEntity<NewsDTO> deleteAllTagsFromNewsByNewsId(@PathVariable
                                                                 @Min(value = 1,
                                                                         message = "news_controller.path_variable.id.in_valid.min")
                                                                 long newsId) throws ServiceException {
        return new ResponseEntity<>(newsService.deleteAllTagsFromNews(newsId), OK);
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
                                          @Min(value = 1,
                                                  message = "news_controller.path_variable.id.in_valid.min")
                                          long id,
                                          @Valid
                                          @RequestBody
                                          @NotNull(message = "news_controller.request_body.news_dto.in_valid.null")
                                          NewsDTO newsDTO) throws ServiceException {
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
                                                       int page) throws ServiceException {
        return new ResponseEntity<>(newsService.getPagination(
                newsService.findAll(page, size),
                newsService.countAllNews(),
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
                                            @Min(value = 1,
                                                    message = "news_controller.path_variable.id.in_valid.min")
                                            long id) throws ServiceException {
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
                                                                 @NotNull(message = "news_controller.path_variable.tag_name.in_valid.null")
                                                                 @NotBlank(message = "news_controller.path_variable.tag_name.in_valid.is_blank")
                                                                 @Size(min = 3, max = 15,
                                                                         message = "news_controller.path_variable.tag_name.in_valid.size")
                                                                 String tagName,
                                                                 @RequestAttribute(value = "size")
                                                                 int size,
                                                                 @RequestAttribute(value = "page")
                                                                 int page) throws ServiceException {
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
                                                               @Min(value = 1,
                                                                       message = "news_controller.path_variable.id.in_valid.min")
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
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Pagination<NewsDTO>> findNewsByAuthorId(@PathVariable
                                                                  @Min(value = 1,
                                                                          message = "news_controller.path_variable.id.in_valid.min")
                                                                  long authorId,
                                                                  @RequestAttribute(value = "size")
                                                                  int size,
                                                                  @RequestAttribute(value = "page")
                                                                  int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findByAuthorId(authorId, page, size),
                        newsService.findByAuthorId(authorId),
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
    @GetMapping("/author/part-name/{partOfAuthorName}")
    public ResponseEntity<Pagination<NewsDTO>> findNewsByAuthorName(@PathVariable
                                                                    @NotNull(message = "news_controller.path_variable.author_name.in_valid.null")
                                                                    String partOfAuthorName,
                                                                    @RequestAttribute(value = "size")
                                                                    int size,
                                                                    @RequestAttribute(value = "page")
                                                                    int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
                        newsService.findByPartOfAuthorName(partOfAuthorName, page, size),
                        newsService.findByPartOfAuthorName(partOfAuthorName),
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
                                                                     @NotNull(message = "news_controller.path_variable.part_of_news_title.in_valid.null")
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
                                                                       @NotNull(message = "news_controller.path_variable.part_of_news_content.in_valid.null")
                                                                       String partOfContent,
                                                                       @RequestAttribute(value = "size")
                                                                       int size,
                                                                       @RequestAttribute(value = "page")
                                                                       int page)
            throws ServiceException {
        return new ResponseEntity<>(
                newsService.getPagination(
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