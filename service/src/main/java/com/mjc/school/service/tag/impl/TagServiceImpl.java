package com.mjc.school.service.tag.impl;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.converter.TagConverter;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.model.Tag;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.tag.impl.sort.TagSortField.COUNT_NEWS;
import static com.mjc.school.service.tag.impl.sort.TagSortField.NAME;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Log4j2
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final NewsRepository newsRepository;
    private final NewsTagRepository newsTagRepository;
    private final TagConverter tagConverter;
    private final PaginationService paginationService;

    @Transactional
    @Override
    public boolean create(TagDTO tagDTO) throws ServiceBadRequestParameterException {
        if (tagRepository.notExistsByName(tagDTO.getName())) {
            Tag tag = tagConverter.fromDTO(tagDTO);
            tagRepository.save(tag);
            return true;
        } else {
            log.log(WARN, "Tag with entered name '%s' already exists".formatted(tagDTO.getName()));
            throw new ServiceBadRequestParameterException("tag_dto.name.not_valid.exists_tag_by_name");
        }
    }

    @Transactional
    @Override
    public boolean addToNews(long tagId, long newsId) throws ServiceBadRequestParameterException {
        NewsTag newsTag = NewsTag.builder()
                .tag(tagRepository.findById(tagId).orElseThrow(() -> {
                    log.log(WARN, "Not found tag by ID: " + tagId);
                    return new ServiceBadRequestParameterException("service.exception.not_found_tag_by_id");
                }))
                .news(newsRepository.findById(newsId).orElseThrow(() -> {
                    log.log(WARN, "Not found news by ID: " + newsId);
                    return new ServiceBadRequestParameterException("service.exception.not_found_news_by_id");
                }))
                .build();
        if (isNotPresentTagInNews(newsTag.getTag(), newsTag.getNews())) {
            newsTagRepository.save(newsTag);
            return true;
        } else {
            log.log(WARN, "Tag with entered ID '" + tagId + "' exists in news");
            throw new ServiceBadRequestParameterException("service.exception.exists_tag_in_news");
        }
    }

    @Transactional
    @Override
    public boolean deleteFromNews(long tagId, long newsId) throws ServiceBadRequestParameterException {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> {
            log.log(WARN, "Not found tag by ID: " + tagId);
            return new ServiceBadRequestParameterException("service.exception.not_found_tag_by_id");
        });
        News news = newsRepository.findById(newsId).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + newsId);
            return new ServiceBadRequestParameterException("service.exception.not_found_news_by_id");
        });
        if (!isNotPresentTagInNews(tag, news)) {
            tagRepository.deleteFromNews(tagId, newsId);
        }
        return true;

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
    public boolean deleteFromAllNews(long tagId) throws ServiceBadRequestParameterException {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteFromAllNewsById(tagId);
            return true;
        } else {
            log.log(WARN, "Not found tag by ID: " + tagId);
            throw new ServiceBadRequestParameterException("service.exception.not_found_tag_by_id");
        }
    }

    @Transactional
    @Override
    public TagDTO update(TagDTO tagDTO) throws ServiceBadRequestParameterException {
        Tag tag = tagRepository.findById(tagDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found tag by ID: " + tagDTO.getId());
            return new ServiceBadRequestParameterException("service.exception.not_found_tag_by_id");
        });
        if (tag.getName().equals(tagDTO.getName())) {
            return tagConverter.toDTO(tag);
        } else {
            if (tagRepository.notExistsByName(tagDTO.getName())) {
                tagRepository.update(tagDTO.getName(), tagDTO.getId());
                tag.setName(tagDTO.getName());
                return tagConverter.toDTO(tag);
            } else {
                log.log(WARN, "Tag with entered name '" + tagDTO.getName() + "' already exists");
                throw new ServiceBadRequestParameterException("tag_dto.name.not_valid.exists_tag_by_name");
            }
        }
    }

    @Override
    public List<TagDTO> findAll(int page, int size, String sortField, String sortType) throws ServiceNoContentException {
        List<Tag> tagList;
        if (NAME.name().equalsIgnoreCase(sortField)) {
            tagList = findAllSortName(sortType, page, size);
        } else if (COUNT_NEWS.name().equalsIgnoreCase(sortField)) {
            tagList = findAllSortCountNews(sortType, page, size);
        } else {
            tagList = findAllSortId(sortType, page, size);
        }
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags");
            throw new ServiceNoContentException();
        }
    }

    @Override
    public List<TagDTO> findAll(String sortType) throws ServiceNoContentException {
        List<Tag> tagList = findAllSortName(sortType);
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags");
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAll() {
        return tagRepository.countAll();
    }

    @Override
    public TagDTO findById(long id) throws ServiceNoContentException {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found tag with this ID: " + id);
            return new ServiceNoContentException();
        });
        return tagConverter.toDTO(tag);
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName,
                                         int page, int size,
                                         String sortField, String sortType) throws ServiceNoContentException {
        List<Tag> tagList;
        if (NAME.name().equalsIgnoreCase(sortField)) {
            tagList = findByPartOfNameSortName(partOfName, sortType, page, size);
        } else if (COUNT_NEWS.name().equalsIgnoreCase(sortField)) {
            tagList = findByPartOfNameSortCountNews(partOfName, sortType, page, size);
        } else {
            tagList = findByPartOfNameSortId(partOfName, sortType, page, size);
        }
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags by part of name: " + partOfName);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllByPartOfName(String partOfName) {
        return tagRepository.countAllByPartOfName("%" + partOfName + "%");
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId,
                                     int page, int size,
                                     String sortField, String sortType) throws ServiceNoContentException {
        List<Tag> tagList;
        if (NAME.name().equalsIgnoreCase(sortField)) {
            tagList = findByNewsIdSortName(newsId, sortType, page, size);
        } else if (COUNT_NEWS.name().equalsIgnoreCase(sortField)) {
            tagList = findByNewsIdSortCountNews(newsId, sortType, page, size);
        } else {
            tagList = findByNewsIdSortId(newsId, sortType, page, size);
        }
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags by news ID: " + newsId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId, String sortType) throws ServiceNoContentException {
        List<Tag> tagList = findByNewsIdSortName(newsId, sortType);
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tagConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found tags by news ID: " + newsId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllByNewsId(long newsId) {
        return tagRepository.countAllByNewsId(newsId);
    }

    @Override
    public Pagination<TagDTO> getPagination(List<TagDTO> elementsOnPage, long countAllElements, int page, int size) {
        return Pagination
                .<TagDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .countAllEntity(countAllElements)
                .numberPage(page)
                .maxNumberPage(paginationService.calcMaxNumberPage(countAllElements, size))
                .build();
    }


    private boolean isNotPresentTagInNews(Tag tag, News news) {
        return news.getTags()
                .stream()
                .filter(newsTag -> newsTag.getTag().getId() == tag.getId())
                .toList()
                .isEmpty();
    }

    private List<Tag> findAllSortName(String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findAllSortNameAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findAllSortNameDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findAllSortName(String sortType) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findAllSortNameAsc();
        } else {
            tagList = tagRepository.findAllSortNameDesc();
        }
        return tagList;
    }

    private List<Tag> findAllSortCountNews(String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findAllSortCountNewsAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findAllSortCountNewsDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findAllSortId(String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findAllSortIdAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findAllSortIdDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findByPartOfNameSortName(String partOfName, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByPartOfNameSortNameAsc("%" + partOfName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findByPartOfNameSortNameDesc("%" + partOfName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findByPartOfNameSortCountNews(String partOfName, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByPartOfNameSortCountNewsAsc("%" + partOfName + "%", size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findByPartOfNameSortCountNewsDesc("%" + partOfName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findByPartOfNameSortId(String partOfName, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByPartOfNameSortIdAsc("%" + partOfName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findByPartOfNameSortIdDesc("%" + partOfName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findByNewsIdSortName(long newsId, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByNewsIdSortNameAsc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findByNewsIdSortNameDesc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }

    private List<Tag> findByNewsIdSortName(long newsId, String sortType) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByNewsIdSortNameAsc(newsId);
        } else {
            tagList = tagRepository.findByNewsIdSortNameDesc(newsId);
        }
        return tagList;
    }

    private List<Tag> findByNewsIdSortCountNews(long newsId, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findAllSortCountNewsAsc()
                    .stream()
                    .filter(tag -> !tag.getNews()
                            .stream()
                            .filter(newsTag -> newsTag.getNews().getId() == newsId)
                            .toList()
                            .isEmpty())
                    .skip(paginationService.calcNumberFirstElement(page, size))
                    .limit(size)
                    .toList();
        } else {
            tagList = tagRepository.findAllSortCountNewsDesc()
                    .stream()
                    .filter(tag -> !tag.getNews()
                            .stream()
                            .filter(newsTag -> newsTag.getNews().getId() == newsId)
                            .toList()
                            .isEmpty())
                    .skip(paginationService.calcNumberFirstElement(page, size))
                    .limit(size)
                    .toList();
        }
        return tagList;
    }

    private List<Tag> findByNewsIdSortId(long newsId, String sortType, int page, int size) {
        List<Tag> tagList;
        if (ASC.name().equalsIgnoreCase(sortType)) {
            tagList = tagRepository.findByNewsIdSortIdAsc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            tagList = tagRepository.findByNewsIdSortIdDesc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return tagList;
    }
}