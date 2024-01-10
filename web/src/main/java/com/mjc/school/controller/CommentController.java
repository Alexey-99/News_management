package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@CrossOrigin
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
            """, response = CommentDTO.class)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentDTO> create(@Valid
                                             @RequestBody
                                             @NotNull(message = "comment_controller.request_body.comment_dto.in_valid.null")
                                             CommentDTO commentDTO) throws ServiceBadRequestParameterException {
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentDTO> update(@PathVariable
                                             @Min(value = 1,
                                                     message = "comment_controller.path_variable.id.in_valid.min")
                                             long id,
                                             @Valid
                                             @RequestBody
                                             @NotNull(message = "comment_controller.request_body.comment_dto.in_valid.null")
                                             CommentDTO commentDTO) throws ServiceBadRequestParameterException {
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteByNewsId(@PathVariable
                                                  @Min(value = 1,
                                                          message = "comment_controller.path_variable.id.in_valid.min")
                                                  long newsId) {
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
                                                          String sortingType) throws ServiceNoContentException {
        List<CommentDTO> commentDTOList;
        try {
            commentDTOList = commentService.findAll(page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                commentDTOList = commentService.findAll(page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(commentService.getPagination(
                commentDTOList, commentService.countAllComments(),
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
                                                               String sortingType) throws ServiceNoContentException {
        List<CommentDTO> commentDTOList;
        try {
            commentDTOList = commentService.findByNewsId(newsId, page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                commentDTOList = commentService.findByNewsId(newsId, page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(commentService.getPagination(
                commentDTOList, commentService.countAllCommentsByNewsId(newsId),
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
                                               long id) throws ServiceNoContentException {
        return new ResponseEntity<>(commentService.findById(id), OK);
    }
}