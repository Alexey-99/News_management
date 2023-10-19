package com.mjc.school.controller;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
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
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/tag")
@Api(value = "Operations for tag in the application")
public class TagController {
    private final TagService tagService;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Create a tag.
            Response: true - if successful created tag, if didn't create tag - false.
            """, response = Boolean.class)
    @PostMapping
    public ResponseEntity<Boolean> create(@Valid
                                          @RequestBody
                                          @NotNull(message = "tag_controller.request_body.tag_dto.in_valid.null")
                                          TagDTO tagDTO) throws ServiceException {
        boolean result = tagService.create(tagDTO);
        return new ResponseEntity<>(result, CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful added a tag to news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Add a tag to news.
            Response: true - if successful added a tag to news, if didn't add a tag to news - false.
            """, response = Boolean.class)
    @PutMapping("/to-news")
    public ResponseEntity<Boolean> addToNews(@RequestParam(value = "tag")
                                             @Min(value = 1,
                                                     message = "tag_controller.request_body.tag_id.in_valid.min")
                                             long tagId,
                                             @RequestParam(value = "news")
                                             @Min(value = 1,
                                                     message = "tag_controller.request_body.news_id.in_valid.min")
                                             long newsId) {
        boolean result = tagService.addToNews(tagId, newsId);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a tag from news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag from news.
            Response: true - if successful deleted a tag from news, if didn't delete a tag from news - false.
            """, response = Boolean.class)
    @DeleteMapping("/from-news")
    public ResponseEntity<Boolean> deleteFromNews(@RequestParam(value = "tag")
                                                  @Min(value = 1,
                                                          message = "tag_controller.request_body.tag_id.in_valid.min")
                                                  long tagId,
                                                  @RequestParam(value = "news")
                                                  @Min(value = 1,
                                                          message = "tag_controller.request_body.news_id.in_valid.min")
                                                  long newsId) {
        boolean result = tagService.deleteFromNews(tagId, newsId);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag by id.
            Response: true - if successful deleted a tag, if didn't delete a tag - false.
            """, response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable
                                              @Min(value = 1,
                                                      message = "tag_controller.request_body.tag_id.in_valid.min")
                                              long id) {
        boolean result = tagService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a tag from all news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag from all news by tag id.
            Response: true - if successful deleted a tag from all news, if didn't delete a tag from all news - false.
            """, response = Boolean.class)
    @DeleteMapping("/all-news/{id}")
    public ResponseEntity<Boolean> deleteFromAllNews(@PathVariable
                                                     @Min(value = 1,
                                                             message = "tag_controller.request_body.tag_id.in_valid.min")
                                                     long id) {
        boolean result = tagService.deleteFromAllNews(id);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful update a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Update a tag by id.
            Response: true - if successful updated a tag, if didn't update a tag - false.
            """, response = Boolean.class)
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> update(@PathVariable
                                         @Min(value = 1,
                                                 message = "tag_controller.request_body.tag_id.in_valid.min")
                                         long id,
                                         @Valid
                                         @RequestBody
                                         @NotNull(message = "tag_controller.request_body.tag_dto.in_valid.null")
                                         TagDTO tagDTO)
            throws ServiceException {
        tagDTO.setId(id);
        TagDTO result = tagService.update(tagDTO);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all tags.
            Response: pagination of tags.
            """, response = Pagination.class)
    @GetMapping("/all")
    public ResponseEntity<Pagination<TagDTO>> findAll(@RequestAttribute(value = "size")
                                                      int size,
                                                      @RequestAttribute(value = "page")
                                                      int page)
            throws ServiceException {
        return new ResponseEntity<>(
                tagService.getPagination(
                        tagService.findAll(page, size),
                        tagService.findAll(),
                        page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View tag by id.
            Response: tags.
            """, response = TagDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> findById(@PathVariable
                                           @Min(value = 1,
                                                   message = "tag_controller.request_body.tag_id.in_valid.min")
                                           long id)
            throws ServiceException {
        return new ResponseEntity<>(tagService.findById(id), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View tag by part of name.
            Response: pagination of tags.
            """, response = Pagination.class)
    @GetMapping("/part-name/{partOfName}")
    public ResponseEntity<Pagination<TagDTO>> findByPartOfName(@PathVariable
                                                               @NotNull(message = "news_controller.path_variable.part_of_tag_name.in_valid.null")
                                                               String partOfName,
                                                               @RequestAttribute(value = "size")
                                                               int size,
                                                               @RequestAttribute(value = "page")
                                                               int page)
            throws ServiceException {
        return new ResponseEntity<>(
                tagService.getPagination(
                        tagService.findByPartOfName(partOfName, page, size),
                        tagService.findByPartOfName(partOfName),
                        page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View tag by news id.
            Response: pagination of tags.
            """, response = Pagination.class)
    @GetMapping("/news/{newsId}")
    public ResponseEntity<Pagination<TagDTO>> findByNewsId(@PathVariable
                                                           @Min(value = 1,
                                                                   message = "tag_controller.request_body.news_id.in_valid.min")
                                                           long newsId,
                                                           @RequestAttribute(value = "size")
                                                           int size,
                                                           @RequestAttribute(value = "page")
                                                           int page)
            throws ServiceException {
        return new ResponseEntity<>(
                tagService.getPagination(
                        tagService.findByNewsId(newsId, page, size),
                        tagService.findByNewsId(newsId),
                        page, size), OK);
    }
}