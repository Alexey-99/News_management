package com.mjc.school.service.tag.impl;

import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.entity.Tag;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.impl.news.NewsRepository;
import com.mjc.school.repository.impl.tag.TagRepository;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_TAGS_WITH_NEWS_ID;
import static org.apache.logging.log4j.Level.WARN;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagConverter tagConverter;
    @Autowired
    private PaginationService<TagDTO> tagPagination;

    @Override
    public boolean create(TagDTO tagDTO) {
        Tag tag = tagConverter.fromDTO(tagDTO);
        return tagRepository.create(tag);
    }

    @Override
    public boolean addToNews(long tagId, long newsId) {
        return (tagRepository.findById(tagId) != null
                && newsRepository.findById(newsId) != null)
                && tagRepository.addToNews(tagId, newsId);
    }

    @Override
    public boolean removeFromNews(long tagId, long newsId) {
        return tagRepository.removeFromNews(tagId, newsId);
    }

    @Override
    public boolean deleteById(long tagId) {
        tagRepository.deleteAllTagsFromNewsByNewsId(tagId);
        tagRepository.deleteById(tagId);
        return tagRepository.findById(tagId) == null;
    }

    @Override
    public boolean deleteFromAllNews(long tagId) {
        return tagRepository.deleteAllTagsFromNewsByNewsId(tagId);
    }

    @Override
    public boolean update(TagDTO tagDTO)
            throws ServiceException {
        Tag tag = tagConverter.fromDTO(tagDTO);
        return tagRepository.update(tag) != null;
    }

    @Override
    public List<TagDTO> findAll(int page, int size)
            throws ServiceException {
        List<Tag> tagList = tagRepository.findAll();
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tag -> tagConverter.toDTO(tag))
                    .toList();
        } else {
            log.log(WARN, "Not found tags");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public TagDTO findById(long id)
            throws ServiceException {
        Tag tag = tagRepository.findById(id);
        if (tag != null) {
            return tagConverter.toDTO(tag);
        } else {
            log.log(WARN, "Not found tag with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException {
        String pattern = partOfName.toLowerCase();
        Pattern p = Pattern.compile(pattern);
        List<Tag> tagList = tagRepository.findAll()
                .stream()
                .filter(tag -> {
                    String tagName = tag.getName().toLowerCase();
                    return (p.matcher(tagName).find())
                            || (p.matcher(tagName).lookingAt())
                            || (tagName.matches(pattern));
                }).toList();
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tag -> tagConverter.toDTO(tag))
                    .toList();
        } else {
            log.log(WARN,
                    "Not found tags with this part of name: " + partOfName);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
        }
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId, int page, int size)
            throws ServiceException {
        List<Tag> tagList = tagRepository.findByNewsId(newsId, page, size);
        if (!tagList.isEmpty()) {
            return tagList.stream()
                    .map(tag -> tagConverter.toDTO(tag))
                    .toList();
        } else {
            log.log(WARN, "Not found tags with news ID: " + newsId);
            throw new ServiceException(NO_TAGS_WITH_NEWS_ID);
        }
    }

    @Override
    public Pagination<TagDTO> getPagination(
            List<TagDTO> list, int page, int size) {
        return tagPagination.getPagination(list, size, page);
    }
}