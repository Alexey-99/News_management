package com.mjc.school.controller;

import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
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
@RequestMapping("/api/v2/comment")
@Api(value = "Operations for comments in the application")
public class CommentController {
    private final CommentService commentService;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created a comment"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Create a comment.
            Response: true - if successful created comment, if didn't create comment - false.
            """, response = Boolean.class)
    @PostMapping
    public ResponseEntity<Boolean> create(@Valid
                                          @RequestBody
                                          @NotNull(message = "comment_controller.request_body.comment_dto.in_valid.null")
                                          CommentDTO commentDTO) throws ServiceException {
        return new ResponseEntity<>(commentService.create(commentDTO), CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful updated a comment"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Update a comment.
            Response: true - if successful updated comment, if didn't update comment - false.
            """, response = Boolean.class)
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable
                                             @Min(value = 1,
                                                     message = "comment_controller.path_variable.id.in_valid.min")
                                             long id,
                                             @Valid
                                             @RequestBody
                                             @NotNull(message = "comment_controller.request_body.comment_dto.in_valid.null")
                                             CommentDTO commentDTO) throws ServiceException {
        commentDTO.setId(id);
        return new ResponseEntity<>(commentService.update(commentDTO), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a comment"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a comment by id.
            Response: true - if successful deleted comment, if didn't delete comment - false.
            """, response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable
                                              @Min(value = 1,
                                                      message = "comment_controller.path_variable.id.in_valid.min")
                                              long id) {
        return new ResponseEntity<>(commentService.deleteById(id), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a comment"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a comment by news id.
            Response: true - if successful deleted comment, if didn't delete comment - false.
            """, response = Boolean.class)
    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsId(@PathVariable
                                                  @Min(value = 1,
                                                          message = "comment_controller.path_variable.id.in_valid.min")
                                                  long newsId) throws ServiceException {
        return new ResponseEntity<>(commentService.deleteByNewsId(newsId), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all comments.
            Response: pagination of comments.
            """, response = Pagination.class)
    @GetMapping("/all")
    public ResponseEntity<Pagination<CommentDTO>> findAll(@RequestAttribute(value = "size")
                                                          int size,
                                                          @RequestAttribute(value = "page")
                                                          int page,
                                                          @RequestParam(value = "sort-field", required = false)
                                                          String sortingField,
                                                          @RequestParam(value = "sort-type", required = false)
                                                          String sortingType) throws ServiceException {
        return new ResponseEntity<>(commentService.getPagination(
                commentService.findAll(page, size, sortingField, sortingType),
                commentService.countAllComments(),
                page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View comments by news id.
            Response: pagination of comments.
            """, response = Pagination.class)
    @GetMapping("/news/{newsId}")
    public ResponseEntity<Pagination<CommentDTO>> findByNewsId(@PathVariable
                                                               @Min(value = 1,
                                                                       message = "comment_controller.path_variable.id.in_valid.min")
                                                               long newsId,
                                                               @RequestAttribute(value = "size")
                                                               int size,
                                                               @RequestAttribute(value = "page")
                                                               int page,
                                                               @RequestParam(value = "sort-field", required = false)
                                                               String sortingField,
                                                               @RequestParam(value = "sort-type", required = false)
                                                               String sortingType) throws ServiceException {
        return new ResponseEntity<>(commentService.getPagination(
                commentService.findByNewsId(newsId, page, size, sortingField, sortingType),
                commentService.countAllCommentsByNewsId(newsId),
                page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View comment by id.
            """, response = CommentDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable
                                               @Min(value = 1,
                                                       message = "comment_controller.path_variable.id.in_valid.min")
                                               long id) throws ServiceException {
        return new ResponseEntity<>(commentService.findById(id), OK);
    }
}