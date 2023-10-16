package com.mjc.school.service.comment.impl;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.Comment;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.logic.handler.DateHandler;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.service.comment.impl.comparator.SortCommentComparator;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeAsc;
import com.mjc.school.service.comment.impl.comparator.impl.created.SortCommentComparatorByCreatedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeDesc;
import com.mjc.school.service.comment.impl.comparator.impl.modified.SortCommentComparatorByModifiedDateTimeAsc;
import com.mjc.school.validation.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.SORT_ERROR;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LogManager.getLogger();
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentConverter commentConverter;
    private final DateHandler dateHandler;
    private final PaginationService<CommentDTO> commentPagination;

    @Transactional
    @Override
    public boolean create(CommentDTO commentDTO) {
        Comment comment = commentConverter.fromDTO(commentDTO);
        comment.setNews(newsRepository.getById(comment.getNewsId()));
        comment.setCreated(dateHandler.getCurrentDate());
        comment.setModified(dateHandler.getCurrentDate());
        commentRepository.save(comment);
        return commentRepository.existsById(comment.getId());
    }

    @Transactional
    @Override
    public CommentDTO update(CommentDTO commentDTO)
            throws ServiceException {
        if (commentRepository.existsById(commentDTO.getId())) {
            Comment commentOld = commentRepository.getById(commentDTO.getId());
            Comment comment = commentConverter.fromDTO(commentDTO);
            comment.setNews(newsRepository.getById(comment.getNewsId()));
            comment.setCreated(commentOld.getCreated());
            comment.setModified(dateHandler.getCurrentDate());
            commentRepository.update(
                    comment.getContent(),
                    comment.getNews().getId(),
                    comment.getModified());
            return commentConverter.toDTO(
                    commentRepository.getById(comment.getId()));
        } else {
            log.log(WARN, "Not found object with this ID: " + commentDTO.getId());
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }

    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        commentRepository.deleteById(id);
        return !commentRepository.existsById(id);
    }

    @Transactional
    @Override
    public boolean deleteByNewsId(long newsId) {
        commentRepository.deleteByNewsId(newsId);
        return commentRepository.findByNewsId(newsId).isEmpty();
    }

    @Override
    public List<CommentDTO> findByNewsId(long newsId, int page, int size)
            throws ServiceException {
        List<Comment> commentList = commentRepository.findByNewsId(newsId,
                commentPagination.calcNumberFirstElement(page, size),
                size);
        if (!commentList.isEmpty()) {
            return commentList.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(ERROR, "Not found objects with comment news ID: " + newsId);
            throw new ServiceException(NO_ENTITY_WITH_COMMENT_NEWS_ID);
        }
    }

    @Override
    public List<CommentDTO> findByNewsId(long newsId) {
        return commentRepository.findByNewsId(newsId)
                .stream()
                .map(commentConverter::toDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> findAll(int page, int size) throws ServiceException {
        Page<Comment> commentPage = commentRepository.findAll(
                PageRequest.of(commentPagination.calcMaxNumberPage(page, size), size));
        if (!commentPage.isEmpty()) {
            return commentPage.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<CommentDTO> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentConverter::toDTO)
                .toList();
    }

    @Override
    public CommentDTO findById(long id)
            throws ServiceException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return commentConverter.toDTO(comment.get());
        } else {
            log.log(WARN, "Not found object with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
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
    public Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage,
                                                List<CommentDTO> allElementsList,
                                                int page, int size) {
        return commentPagination.getPagination(
                elementsOnPage, allElementsList,
                page, size);
    }
}