package com.mjc.school.service.comment.impl;

import com.mjc.school.converter.CommentConverter;
import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeAsc;
import com.mjc.school.validation.CommentValidator;
import com.mjc.school.validation.dto.CommentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.INSERT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.UPDATE_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CommentValidator commentValidator;
    @Autowired
    private CommentConverter commentConverter;
    @Autowired
    private DateHandler dateHandler;
    @Autowired
    private PaginationService<CommentDTO> commentPagination;

    @Override
    public List<CommentDTO> findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (commentValidator.validateNewsId(newsId)) {
                List<Comment> commentList = commentRepository.findByNewsId(newsId);
                if (!commentList.isEmpty()) {
                    for (Comment comment : commentList) {
                        comment.setNews(
                                newsRepository.findById(
                                        comment.getNews().getId()));
                    }
                    return commentList.stream()
                            .map(comment -> commentConverter.toDTO(comment))
                            .toList();
                } else {
                    log.log(ERROR, "Not found objects with comment news ID: " + newsId);
                    throw new ServiceException(NO_ENTITY_WITH_COMMENT_NEWS_ID);
                }
            } else {
                return new ArrayList<>();
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<CommentDTO> findAll() throws ServiceException {
        try {
            List<Comment> commentList = commentRepository.findAll();
            if (!commentList.isEmpty()) {
                for (Comment comment : commentList) {
                    comment.setNews(
                            newsRepository.findById(
                                    comment.getNews().getId()));
                }
                return commentList.stream()
                        .map(comment -> commentConverter.toDTO(comment))
                        .toList();
            } else {
                log.log(WARN, "Not found objects");
                throw new ServiceException(NO_ENTITY);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public CommentDTO findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (commentValidator.validateId(id)) {
                Comment comment = commentRepository.findById(id);
                if (comment != null) {
                    comment.setNews(
                            newsRepository.findById(
                                    comment.getNews().getId()));
                    return commentConverter.toDTO(comment);
                } else {
                    log.log(WARN, "Not found object with this ID: " + id);
                    throw new ServiceException(NO_ENTITY_WITH_ID);
                }
            } else {
                return null;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    @Override
    public List<CommentDTO> sort(List<CommentDTO> list,
                                 SortCommentComparator comparator)
            throws ServiceException {
        List<CommentDTO> sortedList;
        if (list != null) {
            if (comparator != null) {
                sortedList = new LinkedList<>(list);
                sortedList.sort(comparator);
                return sortedList;
            } else {
                log.log(ERROR, "comparator is null");
                throw new ServiceException(SORT_ERROR);
            }
        } else {
            log.log(ERROR, "list is null");
            throw new ServiceException(SORT_ERROR);
        }

    }

    @Override
    public List<CommentDTO> sortByCreatedDateTimeAsc(List<CommentDTO> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeAsc());
    }

    @Override
    public List<CommentDTO> sortByCreatedDateTimeDesc(List<CommentDTO> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeDesc());
    }

    @Override
    public List<CommentDTO> sortByModifiedDateTimeAsc(List<CommentDTO> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeAsc());
    }

    @Override
    public List<CommentDTO> sortByModifiedDateTimeDesc(List<CommentDTO> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeDesc());
    }

    @Override
    public boolean create(CommentDTO commentDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            if (commentValidator.validate(commentDTO)) {
                commentDTO.setCreated(dateHandler.getCurrentDate());
                commentDTO.setModified(dateHandler.getCurrentDate());
                return commentRepository.create(
                        commentConverter.fromDTO(commentDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(INSERT_ERROR);
        }
    }

    @Override
    public boolean update(CommentDTO commentDTO)
            throws ServiceException, IncorrectParameterException {
        try {
            if (commentValidator.validateId(commentDTO.getId()) &&
                    commentValidator.validate(commentDTO)) {
                commentDTO.setModified(dateHandler.getCurrentDate());
                return commentRepository.update(
                        commentConverter.fromDTO(commentDTO));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(UPDATE_ERROR);
        }
    }

    @Override
    public boolean deleteById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            if (commentValidator.validateId(id)) {
                return commentRepository.deleteById(id);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public boolean deleteByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return commentValidator.validateNewsId(newsId) &&
                    commentRepository.deleteByNewsId(newsId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    @Override
    public Pagination<CommentDTO> getPagination(
            List<CommentDTO> list, long numberElementsReturn, long numberPage) {
        return commentPagination.getPagination(list, numberElementsReturn, numberPage);
    }
}