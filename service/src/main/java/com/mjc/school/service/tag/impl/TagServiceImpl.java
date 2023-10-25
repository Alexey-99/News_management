package com.mjc.school.service.tag.impl;

import com.mjc.school.NewsTag;
import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.NewsTagRepository;
import com.mjc.school.service.SortType;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.Tag;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.NewsRepository;
import com.mjc.school.TagRepository;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.SortType.getSortType;
import static com.mjc.school.service.tag.impl.sort.TagSortField.NAME;
import static com.mjc.school.service.tag.impl.sort.TagSortField.getSortField;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private static final Logger log = LogManager.getLogger();
    private final TagRepository tagRepository;
    private final NewsRepository newsRepository;
    private final NewsTagRepository newsTagRepository;
    private final TagConverter tagConverter;
    private final PaginationService<TagDTO> tagPagination;

    @Transactional
    @Override
    public boolean create(TagDTO tagDTO) throws ServiceException {
        if (tagRepository.existsByName(tagDTO.getName())) {
            Tag tag = tagConverter.fromDTO(tagDTO);
            tagRepository.save(tag);
            return true;
        } else {
            log.log(WARN, "Tag with entered name '" + tagDTO.getName() + "' already exists");
            throw new ServiceException("tag_dto.name.not_valid.exists_tag_by_name");
        }
    }

    @Transactional
    @Override
    public boolean addToNews(long tagId, long newsId) throws ServiceException {
        NewsTag newsTag = NewsTag.builder()
                .tag(tagRepository.findById(tagId).orElseThrow(() ->
                        new ServiceException("service.exception.not_found_tag_by_id")))
                .news(newsRepository.findById(newsId).orElseThrow(() ->
                        new ServiceException("service.exception.not_found_news_by_id")))
                .build();
        if (isNotExistsTagInNews(tagId, newsId)) {
            newsTagRepository.save(newsTag);
            return true;
        } else {
            log.log(WARN, "Tag with entered ID '" + tagId + "' exists in news");
            throw new ServiceException("service.exception.exists_tag_in_news");
        }
    }

    @Transactional
    @Override
    public boolean deleteFromNews(long tagId, long newsId) throws ServiceException {
        if (!tagRepository.existsById(tagId)) {
            log.log(WARN, "Not found tag by ID: " + tagId);
            throw new ServiceException("service.exception.not_found_tag_by_id");
        } else if (!newsRepository.existsById(newsId)) {
            log.log(WARN, "Not found news by ID: " + newsId);
            throw new ServiceException("service.exception.not_found_news_by_id");
        } else {
            if (!isNotExistsTagInNews(tagId, newsId)) {
                tagRepository.deleteFromNews(tagId, newsId);
            }
            return true;
        }
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
    public boolean deleteFromAllNews(long tagId) throws ServiceException {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteFromAllNewsById(tagId);
            return true;
        } else {
            log.log(WARN, "Not found tag by ID: " + tagId);
            throw new ServiceException("service.exception.not_found_tag_by_id");
        }
    }

    @Transactional
    @Override
    public TagDTO update(TagDTO tagDTO) throws ServiceException {
        Tag tag = tagRepository.findById(tagDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found tag by ID: " + tagDTO.getId());
            return new ServiceException("service.exception.not_found_tag_by_id");
        });
        if (tag.getName().equals(tagDTO.getName())) {
            return tagConverter.toDTO(tag);
        } else {
            if (!tagRepository.existsByName(tagDTO.getName())) {
                tagRepository.update(tagDTO.getName(), tagDTO.getId());
                tag.setName(tagDTO.getName());
                return tagConverter.toDTO(tag);
            } else {
                log.log(WARN, "Tag with entered name '" + tagDTO.getName() + "' already exists");
                throw new ServiceException("tag_dto.name.not_valid.exists_tag_by_name");
            }
        }
    }

    @Override
    public List<TagDTO> findAll(int page, int size, String sortField, String sortType) throws ServiceException {
        Page<Tag> tagPage = tagRepository.findAll(PageRequest.of(
                tagPagination.calcNumberFirstElement(page, size), size,
                Sort.by(fromOptionalString(sortType).orElse(ASC),
                        getSortField(sortField).orElse(NAME.name().toLowerCase()))));
        if (!tagPage.isEmpty()) {
            return tagPage.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags");
            throw new ServiceException("service.exception.not_found_tags");
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
    public long countAll() {
        return tagRepository.countAll();
    }

    @Override
    public TagDTO findById(long id) throws ServiceException {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found tag with this ID: " + id);
            return new ServiceException("service.exception.not_found_tag_by_id");
        });
        return tagConverter.toDTO(tag);
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName, int page, int size,
                                         String sortField, String sortType) throws ServiceException {
        List<Tag> tagList = tagRepository.findByPartOfName("%" + partOfName + "%",
                PageRequest.of(tagPagination.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortType).orElse(ASC),
                                getSortField(sortField).orElse(NAME.name().toLowerCase())))
        );
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags by part of name: " + partOfName);
            throw new ServiceException("service.exception.not_found_tags_by_part_of_name");
        }
    }

    @Override
    public long countAllByPartOfName(String partOfName) {
        return tagRepository.countAllByPartOfName("%" + partOfName + "%");
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId, int page, int size,
                                     String sortField, String sortType) throws ServiceException {
        List<Tag> tagList = tagRepository.findByNewsId(newsId,
                PageRequest.of(tagPagination.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortType).orElse(ASC),
                                getSortField(sortField).orElse(NAME.name().toLowerCase()))));
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags by news ID: " + newsId);
            throw new ServiceException("service.exception.not_found_tags_by_news_id");
        }
    }

    @Override
    public long countAllByNewsId(long newsId) {
        return tagRepository.countAllByNewsId(newsId);
    }

    @Override
    public Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage, long countAllElements, int page, int size) {
        return tagPagination.getPagination(elementsOnPage, countAllElements, page, size);
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