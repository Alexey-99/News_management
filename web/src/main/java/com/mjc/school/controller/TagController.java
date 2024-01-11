package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
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

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/tag")
@Api(value = "Operations for tag in the application")
@CrossOrigin
public class TagController {
    private final TagService tagService;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful created a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Create a tag.
            Response: true - if successful created tag, if didn't create tag - false.
            """, response = Boolean.class)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> create(@Valid
                                          @RequestBody
                                          @NotNull(message = "tag_controller.request_body.tag_dto.in_valid.null")
                                          TagDTO tagDTO) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(tagService.create(tagDTO), CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful added a tag to news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Add a tag to news.
            Response: true - if successful added a tag to news, if didn't add a tag to news - false.
            """, response = Boolean.class)
    @PutMapping("/to-news")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> addToNews(@RequestParam(value = "tag")
                                             @Min(value = 1,
                                                     message = "tag_controller.request_body.tag_id.in_valid.min")
                                             long tagId,
                                             @RequestParam(value = "news")
                                             @Min(value = 1,
                                                     message = "tag_controller.request_body.news_id.in_valid.min")
                                             long newsId) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(tagService.addToNews(tagId, newsId), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deleted a tag from news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag from news.
            Response: true - if successful deleted a tag from news, if didn't delete a tag from news - false.
            """, response = Boolean.class)
    @DeleteMapping("/from-news")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteFromNews(@RequestParam(value = "tag")
                                                  @Min(value = 1,
                                                          message = "tag_controller.request_body.tag_id.in_valid.min")
                                                  Long tagId,
                                                  @RequestParam(value = "news")
                                                  @Min(value = 1,
                                                          message = "tag_controller.request_body.news_id.in_valid.min")
                                                  Long newsId) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(tagService.deleteFromNews(tagId, newsId), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deleted a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag by id.
            Response: true - if successful deleted a tag, if didn't delete a tag - false.
            """, response = Boolean.class)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteById(@PathVariable
                                              @Min(value = 1,
                                                      message = "tag_controller.request_body.tag_id.in_valid.min")
                                              long id) {
        return new ResponseEntity<>(tagService.deleteById(id), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful deleted a tag from all news"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Delete a tag from all news by tag id.
            Response: true - if successful deleted a tag from all news, if didn't delete a tag from all news - false.
            """, response = Boolean.class)
    @DeleteMapping("/all-news/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteFromAllNews(@PathVariable
                                                     @Min(value = 1,
                                                             message = "tag_controller.request_body.tag_id.in_valid.min")
                                                     long id) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(tagService.deleteFromAllNews(id), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update a tag"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            Update a tag by id.
            Response: true - if successful updated a tag, if didn't update a tag - false.
            """, response = Boolean.class)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDTO> update(@PathVariable
                                         @Min(value = 1,
                                                 message = "tag_controller.request_body.tag_id.in_valid.min")
                                         long id,
                                         @Valid
                                         @RequestBody
                                         @NotNull(message = "tag_controller.request_body.tag_dto.in_valid.null")
                                         TagDTO tagDTO) throws ServiceBadRequestParameterException {
        tagDTO.setId(id);
        return new ResponseEntity<>(tagService.update(tagDTO), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all tags.
            Response: pagination of tags.
            """, response = Pagination.class)
    @GetMapping("/all/page")
    public ResponseEntity<Pagination<TagDTO>> findAll(@RequestAttribute(value = "size")
                                                      int size,
                                                      @RequestAttribute(value = "page")
                                                      int page,
                                                      @RequestParam(value = "sort-field", required = false)
                                                      String sortingField,
                                                      @RequestParam(value = "sort-type", required = false)
                                                      String sortingType) throws ServiceNoContentException {
        List<TagDTO> tagDTOList;
        try {
            tagDTOList = tagService.findAll(page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                tagDTOList = tagService.findAll(page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(tagService.getPagination(
                tagDTOList, tagService.countAll(),
                page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all tags.
            Response: list of tags.
            """, response = List.class)
    @GetMapping("/all")
    public ResponseEntity<List<TagDTO>> findAll(@RequestParam(value = "sort-type", required = false)
                                                String sortType) throws ServiceNoContentException {
        return new ResponseEntity<>(tagService.findAll(sortType), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
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
                                           long id) throws ServiceNoContentException {
        return new ResponseEntity<>(tagService.findById(id), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
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
                                                               int page,
                                                               @RequestParam(value = "sort-field", required = false)
                                                               String sortingField,
                                                               @RequestParam(value = "sort-type", required = false)
                                                               String sortingType) throws ServiceNoContentException {

        List<TagDTO> tagDTOList;
        try {
            tagDTOList = tagService.findByPartOfName(partOfName, page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                tagDTOList = tagService.findByPartOfName(partOfName, page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(tagService.getPagination(
                tagDTOList, tagService.countAllByPartOfName(partOfName),
                page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View tag by news id.
            Response: pagination of tags.
            """, response = Pagination.class)
    @GetMapping("/news/page/{newsId}")
    public ResponseEntity<Pagination<TagDTO>> findByNewsId(@PathVariable
                                                           @Min(value = 1,
                                                                   message = "tag_controller.request_body.news_id.in_valid.min")
                                                           long newsId,
                                                           @RequestAttribute(value = "size")
                                                           int size,
                                                           @RequestAttribute(value = "page")
                                                           int page,
                                                           @RequestParam(value = "sort-field", required = false)
                                                           String sortingField,
                                                           @RequestParam(value = "sort-type", required = false)
                                                           String sortingType) throws ServiceNoContentException {
        List<TagDTO> tagDTOList;
        try {
            tagDTOList = tagService.findByNewsId(newsId, page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                tagDTOList = tagService.findByNewsId(newsId, page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(tagService.getPagination(
                tagDTOList, tagService.countAllByNewsId(newsId),
                page, size), OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful completed request"),
            @ApiResponse(code = 204, message = "Not found content in data base"),
            @ApiResponse(code = 400, message = "You are entered request parameters incorrectly"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = """
            View all tag by news id.
            Response: list of tags.
            """, response = List.class)
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<TagDTO>> findByNewsId(@PathVariable
                                                     @Min(value = 1,
                                                             message = "tag_controller.request_body.news_id.in_valid.min")
                                                     long newsId,
                                                     @RequestParam(value = "sort-type", required = false)
                                                     String sortingType) throws ServiceNoContentException {
        List<TagDTO> tagDTOList = tagService.findByNewsId(newsId, sortingType);
        return new ResponseEntity<>(tagDTOList, OK);
    }
}