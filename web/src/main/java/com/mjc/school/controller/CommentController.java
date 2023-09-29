package com.mjc.school.controller;

import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.name.SortField;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.BAD_REQUEST_PARAMETER;
import static com.mjc.school.name.SortField.MODIFIED;
import static com.mjc.school.name.SortType.ASCENDING;
import static com.mjc.school.name.SortType.DESCENDING;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/all")
    public Pagination<CommentDTO> findAll(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return commentService.getPagination(
                commentService.findAll(),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/news/{newsId}")
    public Pagination<CommentDTO> findByNewsId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return commentService.getPagination(
                commentService.findByNewsId(newsId),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/{id}")
    public CommentDTO findById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        return commentService.findById(id);
    }

    @GetMapping("/sort")
    public Pagination<CommentDTO> sort(
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
        Pagination<CommentDTO> sortedList = null;
        switch (sortingField) {
            case SortField.CREATED -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = commentService.getPagination(
                            commentService.sortByCreatedDateTimeAsc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = commentService.getPagination(
                            commentService.sortByCreatedDateTimeDesc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
            default -> {
                switch (sortingType) {
                    case ASCENDING -> sortedList = commentService.getPagination(
                            commentService.sortByModifiedDateTimeAsc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                    default -> sortedList = commentService.getPagination(
                            commentService.sortByModifiedDateTimeDesc(
                                    commentService.findAll()),
                            countElementsReturn,
                            numberPage);
                }
            }
        }
        return sortedList;
    }

    @PostMapping
    public ResponseEntity<Boolean> create(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            CommentDTO commentDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.create(commentDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Boolean> update(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            CommentDTO commentDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.update(commentDTO);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Boolean> deleteByNewsId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = commentService.deleteByNewsId(newsId);
        return new ResponseEntity<>(result, OK);
    }
}