package com.mjc.school.controller;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import com.mjc.school.validation.dto.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.BAD_REQUEST_PARAMETER;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v2/author"/*, produces = APPLICATION_JSON_VALUE*/)
@Api("Operations for authors in the application")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created a author"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Create a author.
            Response: true - if successful created author, if didn't create author - false.
            """, response = Boolean.class)
    @PostMapping
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

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful deleted a author"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrect"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a author by id.
            Response: true - if successful deleted author, if didn't delete author - false.
            """, response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful updated a author"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrect"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Update a author by id.
            Response: true - if successful updated author, if didn't update author - false.
            """, response = Boolean.class)
    @PutMapping
    public ResponseEntity<Boolean> update(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            AuthorDTO authorDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = authorService.update(authorDTO);
        return new ResponseEntity<>(result, OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all authors.
            Response: pagination with authors.
            """, response = Pagination.class)
    @GetMapping(
            value = "/all",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagination<AuthorDTO>> findAll(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long size,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long page)
            throws ServiceException {
       return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(authorService.getPagination(
                        authorService.findAll(), size, page)
                );
//        return authorService.getPagination(
//                authorService.findAll(), size, page);

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View author by id
            """, response = AuthorDTO.class)
    @GetMapping("/{id}")
    public AuthorDTO findById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        return authorService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View authors by part of name.
            Response: pagination with authors.
            """, response = Pagination.class)
    @GetMapping("/part-name/{partOfName}")
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View author by news id.
            """, response = AuthorDTO.class)
    @GetMapping("/news/{newsId}")
    public AuthorDTO findByNewsId(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException, IncorrectParameterException {
        return authorService.findByNewsId(newsId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all authors with amount of written news.
            Response: objects with author id and amount written news, with pagination.
            """, response = Pagination.class)
    @GetMapping("/amount-news")
    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO> selectAllAuthorsIdWithAmountOfWrittenNews(
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 404, message = "Entity not found with entered parameters"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View sorted authors id with amount of written news.
            Response: pagination with objects with author id and amount written news.
            """, response = Pagination.class)
    @GetMapping("/sort/amount-news")
    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO>
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