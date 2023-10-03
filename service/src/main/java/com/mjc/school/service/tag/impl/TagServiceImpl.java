package com.mjc.school.service.tag.impl;

import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.entity.Tag;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.repository.tag.TagRepository;
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
import static org.apache.logging.log4j.Level.ERROR;
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
    public boolean create(TagDTO tagDTO)
            throws ServiceException {
        try {
            Tag tag = tagConverter.fromDTO(tagDTO);
            return tagRepository.create(tag);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addToNews(long tagId, long newsId)
            throws ServiceException {
        try {
            return (tagRepository.findById(tagId) != null
                    && newsRepository.findById(newsId) != null)
                    && tagRepository.addToNews(tagId, newsId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeFromNews(long tagId, long newsId)
            throws ServiceException {
        try {
            return tagRepository.removeFromNews(tagId, newsId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteById(long tagId)
            throws ServiceException {
        try {
            tagRepository.deleteAllTagsFromNewsByNewsId(tagId);
            tagRepository.deleteById(tagId);
            return tagRepository.findById(tagId) == null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteFromAllNews(long tagId)
            throws ServiceException {
        try {
            return tagRepository.deleteAllTagsFromNewsByNewsId(tagId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(TagDTO tagDTO)
            throws ServiceException {
        try {
            Tag tag = tagConverter.fromDTO(tagDTO);
            return tagRepository.update(tag);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagDTO> findAll() throws ServiceException {
        try {
            List<Tag> tagList = tagRepository.findAll();
            if (!tagList.isEmpty()) {
                for (Tag tag : tagList) {
                    tag.setNews(newsRepository.findByTagId(tag.getId()));
                }
                return tagList.stream().map(tag -> tagConverter.toDTO(tag)).toList();
            } else {
                log.log(WARN, "Not found tags");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public TagDTO findById(long id)
            throws ServiceException {
        try {
            Tag tag = tagRepository.findById(id);
            if (tag != null) {
                tag.setNews(newsRepository.findByTagId(tag.getId()));
                return tagConverter.toDTO(tag);
            } else {
                log.log(WARN, "Not found tag with this ID: " + id);
                throw new ServiceException(NO_ENTITY_WITH_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagDTO> findByPartOfName(String partOfName)
            throws ServiceException {
        try {
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
                for (Tag tag : tagList) {
                    tag.setNews(newsRepository.findByTagId(tag.getId()));
                }
                return tagList.stream()
                        .map(tag -> tagConverter.toDTO(tag))
                        .toList();
            } else {
                log.log(WARN,
                        "Not found tags with this part of name: " + partOfName);
                throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagDTO> findByNewsId(long newsId)
            throws ServiceException {
        try {
            List<Tag> tagList = tagRepository.findByNewsId(newsId);
            if (!tagList.isEmpty()) {
                for (Tag tag : tagList) {
                    tag.setNews(newsRepository.findByTagId(tag.getId()));
                }
                return tagList.stream()
                        .map(tag -> tagConverter.toDTO(tag))
                        .toList();
            } else {
                log.log(WARN, "Not found tags with news ID: " + newsId);
                throw new ServiceException(NO_TAGS_WITH_NEWS_ID);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Pagination<TagDTO> getPagination(
            List<TagDTO> list, long size, long page) {
        return tagPagination.getPagination(list, size, page);
    }
}