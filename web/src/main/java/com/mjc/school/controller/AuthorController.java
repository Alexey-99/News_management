package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import com.mjc.school.validation.dto.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Validated
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v2/author")
@Api("Operations for authors in the application")
public class AuthorController {
    private final AuthorService authorService;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> create(@Valid
                                          @RequestBody
                                          @NotNull(message = "author_controller.request_body.author_dto.in_valid.null")
                                          AuthorDTO authorDTO) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(authorService.create(authorDTO), CREATED);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteById(@PathVariable
                                              @Min(value = 1,
                                                      message = "author_controller.path_variable.id.in_valid.min")
                                              long id) {
        return new ResponseEntity<>(authorService.deleteById(id), OK);
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
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthorDTO> update(@PathVariable
                                            @Min(value = 1,
                                                    message = "author_controller.path_variable.id.in_valid.min")
                                            long id,
                                            @RequestBody
                                            @Valid
                                            @NotNull(message = "author_controller.request_body.author_dto.in_valid.null")
                                            AuthorDTO authorDTO) throws ServiceBadRequestParameterException {
        authorDTO.setId(id);
        return new ResponseEntity<>(authorService.update(authorDTO), OK);
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
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagination<AuthorDTO>> findAll(@RequestAttribute(value = "size")
                                                         int size,
                                                         @RequestAttribute(value = "page")
                                                         int page,
                                                         @RequestParam(value = "sort-field", required = false)
                                                         String sortingField,
                                                         @RequestParam(value = "sort-type", required = false)
                                                         String sortingType) throws ServiceNoContentException {
        return new ResponseEntity<>(authorService.getPagination(
                authorService.findAll(page, size, sortingField, sortingType),
                authorService.countAll(), page, size), OK);
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
    public ResponseEntity<AuthorDTO> findById(@PathVariable
                                              @Min(value = 1,
                                                      message = "author_controller.path_variable.id.in_valid.min")
                                              long id) throws ServiceNoContentException {
        return new ResponseEntity<>(authorService.findById(id), OK);
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
    public ResponseEntity<Pagination<AuthorDTO>> findByPartOfName(@PathVariable
                                                                  @NotNull(message = "author_controller.path_variable.part_of_tag_name.in_valid.null")
                                                                  String partOfName,
                                                                  @RequestAttribute(value = "size")
                                                                  int size,
                                                                  @RequestAttribute(value = "page")
                                                                  int page,
                                                                  @RequestParam(value = "sort-field", required = false)
                                                                  String sortingField,
                                                                  @RequestParam(value = "sort-type", required = false)
                                                                  String sortingType) throws ServiceNoContentException {
        return new ResponseEntity<>(authorService.getPagination(
                authorService.findByPartOfName(partOfName, page, size, sortingField, sortingType),
                authorService.countAllByPartOfName(partOfName),
                page, size), OK);
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
    public ResponseEntity<AuthorDTO> findByNewsId(@PathVariable
                                                  @Min(value = 1,
                                                          message = "author_controller.path_variable.id.in_valid.min")
                                                  long newsId) throws ServiceNoContentException {
        return new ResponseEntity<>(authorService.findByNewsId(newsId), OK);
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
    public ResponseEntity<Pagination<AuthorIdWithAmountOfWrittenNewsDTO>>
    selectAllAuthorsIdWithAmountOfWrittenNews(@RequestAttribute(value = "size")
                                              int size,
                                              @RequestAttribute(value = "page")
                                              int page,
                                              @RequestParam(value = "sort-type", required = false)
                                              String sortingType) throws ServiceNoContentException {
        return new ResponseEntity<>(authorService.getPaginationAuthorIdWithAmountOfWrittenNews(
                authorService.findAllAuthorsIdWithAmountOfWrittenNews(page, size, sortingType),
                authorService.countAll(), page, size), OK);
    }
}