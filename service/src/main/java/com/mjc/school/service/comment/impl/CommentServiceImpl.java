package com.mjc.school.service.comment.impl;

import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Pagination;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.comment.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeAsc;
import com.mjc.school.validation.CommentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.DELETE_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.FIND_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.INSERT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.UPDATE_ERROR;
import static org.apache.logging.log4j.Level.ERROR;

/**
 * The type Comment service.
 */
@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentValidator commentValidator;
    @Autowired
    private DateHandler dateHandler;
    @Autowired
    private PaginationService<Comment> commentPagination;

    /**
     * Find by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<Comment> findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return commentValidator.validateNewsId(newsId) ?
                    commentRepository.findByNewsId(newsId) : null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> findAll() throws ServiceException {
        try {
            return commentRepository.findAll();
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Find by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public Comment findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            return commentValidator.validateId(id) ?
                    commentRepository.findById(id) : null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(FIND_ERROR);
        }
    }

    /**
     * Sort list.
     *
     * @param list       the list
     * @param comparator the comparator
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> sort(List<Comment> list, SortCommentComparator comparator)
            throws ServiceException {
        List<Comment> sortedList;
        if (list != null) {
            if (comparator != null) {
                sortedList = new LinkedList<>(list);
                sortedList.sort(comparator);
            } else {
                log.log(ERROR, "comparator is null");
                throw new ServiceException(SORT_ERROR);
            }
        } else {
            log.log(ERROR, "list is null");
            throw new ServiceException(SORT_ERROR);
        }
        return sortedList;
    }

    /**
     * Sort by created date time asc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> sortByCreatedDateTimeAsc(List<Comment> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeAsc());
    }

    /**
     * Sort by created date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> sortByCreatedDateTimeDesc(List<Comment> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByCreatedDateTimeDesc());
    }

    /**
     * Sort by modified date time asc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> sortByModifiedDateTimeAsc(List<Comment> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeAsc());
    }

    /**
     * Sort by modified date time desc list.
     *
     * @param list the list
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Comment> sortByModifiedDateTimeDesc(List<Comment> list)
            throws ServiceException {
        return sort(list, new SortCommentComparatorByModifiedDateTimeDesc());
    }

    /**
     * Create comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean create(Comment comment)
            throws ServiceException, IncorrectParameterException {
        try {
            if (comment != null &&
                    commentValidator.validate(comment)) {
                comment.setCreated(dateHandler.getCurrentDate());
                comment.setModified(dateHandler.getCurrentDate());
                return commentRepository.create(comment);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(INSERT_ERROR);
        }
    }

    /**
     * Update comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean update(Comment comment)
            throws ServiceException, IncorrectParameterException {
        try {
            if (comment != null &&
                    (commentValidator.validateId(comment.getId()) &&
                            commentValidator.validate(comment))) {
                comment.setModified(dateHandler.getCurrentDate());
                return commentRepository.update(comment);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(UPDATE_ERROR);
        }
    }

    /**
     * Delete comment by id.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            return commentValidator.validateId(id) &&
                    commentRepository.deleteById(id);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(DELETE_ERROR);
        }
    }

    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
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

    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    @Override
    public Pagination<Comment> getPagination(
            List<Comment> list, long numberElementsReturn, long numberPage) {
        return commentPagination.getPagination(list, numberElementsReturn, numberPage);
    }
}