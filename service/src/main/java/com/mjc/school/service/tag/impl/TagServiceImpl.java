package com.mjc.school.service.tag.impl;

import com.mjc.school.NewsTag;
import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.Tag;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_TAGS_WITH_NEWS_ID;
import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private static final Logger log = LogManager.getLogger();
    private final TagRepository tagRepository;
    private final NewsRepository newsRepository;
    private final NewsTagRepository newsTagRepository;
    private final TagConverter tagConverter;
    private final PaginationService<TagDTO> tagPagination;

    @Override
    public boolean create(TagDTO tagDTO) throws ServiceException {
        if (!tagRepository.existByName(tagDTO.getName())) {
            Tag tag = tagConverter.fromDTO(tagDTO);
            tagRepository.save(tag);
            return true;
        } else {
            log.log(WARN, "Tag with entered name '" + tagDTO.getName() + "' already exists");
            throw new ServiceException("tag_dto.name.not_valid.exists_tag_by_name");
        }
    }

    @Override
    public boolean addToNews(long tagId, long newsId) {
        boolean result = false;
        NewsTag newsTag = NewsTag.builder()
                .tag(tagRepository.getById(tagId))
                .news(newsRepository.getById(newsId))
                .build();
        if (newsTag.getNews() != null &&
                newsTag.getTag() != null &&
                isNotExistsTagInNews(tagId, newsId)) {
            newsTagRepository.save(newsTag);
            result = true;
        }
        return result;
    }

    @Transactional
    @Override
    public boolean deleteFromNews(long tagId, long newsId) {
        tagRepository.deleteFromNews(tagId, newsId);
        return newsRepository.findById(newsId)
                .filter(value -> !value.getTags()
                        .stream()
                        .filter(tag -> tag.getId() == tagId)
                        .toList()
                        .isEmpty())
                .isEmpty();
    }

    @Transactional
    @Override
    public boolean deleteById(long tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteFromAllNews(long tagId) {
        tagRepository.deleteFromAllNewsById(tagId);
        return newsRepository.findByTagId(tagId).isEmpty();
    }

    @Transactional
    @Override
    public TagDTO update(TagDTO tagDTO) throws ServiceException {
        Optional<Tag> optionalTag = tagRepository.findById(tagDTO.getId());
        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            if (tag.getName().equals(tagDTO.getName())) {
                return tagConverter.toDTO(tag);
            } else {
                if (!tagRepository.existByName(tagDTO.getName())) {
                    tagRepository.update(tagDTO.getName(), tagDTO.getId());
                    tag.setName(tagDTO.getName());
                    return tagConverter.toDTO(tag);
                } else {
                    log.log(WARN, "Tag with entered name '" + tagDTO.getName() + "' already exists");
                    throw new ServiceException("tag_dto.name.not_valid.exists_tag_by_name");
                }
            }
        } else {
            log.log(WARN, "Not found object with this ID: " + tagDTO.getId());
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<TagDTO> findAll(int page, int size)
            throws ServiceException {
        Page<Tag> tagList = tagRepository.findAll(
                PageRequest.of(
                        tagPagination.calcNumberFirstElement(page, size),
                        size));
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<TagDTO> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagConverter::toDTO)
                .toList();
    }

    @Override
    public TagDTO findById(long id)
            throws ServiceException {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            return tagConverter.toDTO(tag.get());
        } else {
            log.log(WARN, "Not found tag with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException {
        String patternPartOfName = "%" + partOfName + "%";
        List<Tag> tagList = tagRepository.findByPartOfName(
                patternPartOfName,
                tagPagination.calcNumberFirstElement(page, size),
                size);
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN,
                    "Not found tags with this part of name: " + partOfName);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
        }
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName) {
        String patternPartOfName = "%" + partOfName + "%";
        return tagRepository.findByPartOfName(patternPartOfName)
                .stream()
                .map(tagConverter::toDTO)
                .toList();
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId, int page, int size)
            throws ServiceException {
        List<Tag> tagList = tagRepository.findByNewsId(
                newsId,
                tagPagination.calcNumberFirstElement(page, size),
                size);
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags with news ID: " + newsId);
            throw new ServiceException(NO_TAGS_WITH_NEWS_ID);
        }
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId) {
        return tagRepository.findByNewsId(newsId)
                .stream()
                .map(tagConverter::toDTO)
                .toList();
    }

    @Override
    public Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage,
                                            List<TagDTO> allElementsList,
                                            int page, int size) {
        return tagPagination.getPagination(
                elementsOnPage, allElementsList,
                page, size);
    }

    private boolean isNotExistsTagInNews(long tagId, long newsId) {
        return newsRepository.getById(newsId)
                .getTags()
                .stream()
                .filter(newsTag -> newsTag.getTag().getId() == tagId)
                .toList()
                .isEmpty();
    }
}