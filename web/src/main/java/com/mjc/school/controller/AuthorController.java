package com.mjc.school.controller;

import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.entity.News;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.BAD_REQUEST_PARAMETER;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/author")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting news in the application")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/amount-news")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public Pagination<AuthorIdWithAmountOfWrittenNews> selectAllAuthorsWithAmountOfWrittenNews(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return authorService.getPaginationAuthorIdWithAmountOfWrittenNews(
                authorService.selectAllAuthorsIdWithAmountOfWrittenNews(),
                countElementsReturn,
                numberPage);
    }

    @PostMapping
    @ApiOperation(value = "Create a piece of news", response = News.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a piece of news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<Boolean> create(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            AuthorDTO authorDTO)
            throws ServiceException,
            IncorrectParameterException {
        boolean result = authorService.create(authorDTO);
        return new ResponseEntity<>(result, CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @PutMapping
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<Boolean> update(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.update(authorDTO);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public Pagination<AuthorDTO> findAllAuthors(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return authorService.getPagination(
                authorService.findAll(),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public AuthorDTO findById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        return authorService.findById(id);
    }

    @GetMapping("/part-name/{partOfName}")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public Pagination<AuthorDTO> findByPartOfName(
            @PathVariable
            @NotNull(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            @NotBlank(message = BAD_PARAMETER_PART_OF_TAG_NAME)
            String partOfName,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException, IncorrectParameterException {
        return authorService.getPagination(
                authorService.findByPartOfName(partOfName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/news/{newsId}")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public AuthorDTO findByNewsId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException, IncorrectParameterException {
        return authorService.findByNewsId(newsId);
    }

    @GetMapping("/sort/amount-news")
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public Pagination<AuthorIdWithAmountOfWrittenNews>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return authorService.getPaginationAuthorIdWithAmountOfWrittenNews(
                authorService.sortAllAuthorsIdWithAmountOfWrittenNewsDesc(),
                countElementsReturn,
                numberPage);
    }
}