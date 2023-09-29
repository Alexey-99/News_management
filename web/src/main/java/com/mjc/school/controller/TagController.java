package com.mjc.school.controller;

import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
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

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.BAD_REQUEST_PARAMETER;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<Boolean> create(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            TagDTO tagDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.create(tagDTO);
        return new ResponseEntity<>(result, CREATED);
    }

    @PutMapping("/to-news")
    public ResponseEntity<Boolean> addToNews(
            @RequestParam(value = "tag")
            @Min(value = 1, message = BAD_ID)
            long tagId,
            @RequestParam(value = "news")
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.addToNews(tagId, newsId);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/from-news")
    public ResponseEntity<Boolean> removeTagFromNews(
            @RequestParam(value = "tag")
            @Min(value = 1, message = BAD_ID)
            long tagId,
            @RequestParam(value = "news")
            @Min(value = 1, message = BAD_ID)
            long newsId)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.removeFromNews(tagId, newsId);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.deleteById(id);
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/all-news/{id}")
    public ResponseEntity<Boolean> deleteFromAllNews(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.deleteFromAllNews(id);
        return new ResponseEntity<>(result, OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> update(
            @Valid
            @RequestBody
            @NotNull(message = BAD_REQUEST_PARAMETER)
            TagDTO tagDTO)
            throws ServiceException, IncorrectParameterException {
        boolean result = tagService.update(tagDTO);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/all")
    public Pagination<TagDTO> findAll(
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = DEFAULT_SIZE)
            long countElementsReturn,
            @RequestParam(value = "page",
                    required = false,
                    defaultValue = DEFAULT_NUMBER_PAGE)
            long numberPage)
            throws ServiceException {
        return tagService.getPagination(
                tagService.findAll(),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/{id}")
    public TagDTO findById(
            @PathVariable
            @Min(value = 1, message = BAD_ID)
            long id)
            throws ServiceException, IncorrectParameterException {
        return tagService.findById(id);
    }

    @GetMapping("/part-name/{partOfName}")
    public Pagination<TagDTO> findByPartOfName(
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
        return tagService.getPagination(
                tagService.findByPartOfName(partOfName),
                countElementsReturn,
                numberPage);
    }

    @GetMapping("/news/{newsId}")
    public Pagination<TagDTO> findByNewsId(
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
        return tagService.getPagination(
                tagService.findByNewsId(newsId), countElementsReturn, numberPage);
    }
}