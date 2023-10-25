package com.mjc.school.service.comment.impl;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.model.Comment;
import com.mjc.school.service.comment.impl.sort.CommentSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.comment.impl.sort.CommentSortField.getSortField;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

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
        return true;
    }

    @Transactional
    @Override
    public CommentDTO update(CommentDTO commentDTO) throws ServiceException {
        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found comment by ID: " + commentDTO.getId());
            return new ServiceException("service.exception.not_found_comment_by_id");
        });
        comment.setContent(commentDTO.getContent());
        comment.setNews(newsRepository.getById(commentDTO.getNewsId()));
        comment.setModified(dateHandler.getCurrentDate());
        commentRepository.update(comment.getContent(), comment.getNews().getId(),
                comment.getModified(), comment.getId());
        return commentConverter.toDTO(comment);


    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteByNewsId(long newsId) {
        if (newsRepository.existsById(newsId)) {
            commentRepository.deleteByNewsId(newsId);
        }
        return true;
    }

    @Override
    public List<CommentDTO> findAll(int page, int size,
                                    String sortingField, String sortingType) throws ServiceException {
        Page<Comment> commentPage = commentRepository.findAll(PageRequest.of(
                commentPagination.calcNumberFirstElement(page, size), size,
                Sort.by(fromOptionalString(sortingType).orElse(DESC),
                        getSortField(sortingField)
                                .orElse(CommentSortField.MODIFIED.name().toLowerCase()))));
        if (commentPage.getSize() > 0) {
            return commentPage.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found comments");
            throw new ServiceException("service.exception.not_found_comments");
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
    public long countAllComments() {
        return commentRepository.countAllComments();
    }

    @Override
    public List<CommentDTO> findByNewsId(long newsId, int page, int size,
                                         String sortingField, String sortingType) throws ServiceException {
        List<Comment> commentList = commentRepository.findByNewsId(newsId,
                PageRequest.of(commentPagination.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(DESC),
                                getSortField(sortingField)
                                        .orElse(CommentSortField.MODIFIED.name().toLowerCase()))));
        if (!commentList.isEmpty()) {
            return commentList.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(ERROR, "Not found comments by news ID: " + newsId);
            throw new ServiceException("service.exception.not_found_comments_by_news_id");
        }
    }

    @Override
    public long countAllCommentsByNewsId(long newsId) {
        return commentRepository.countAllCommentsByNewsId(newsId);
    }

    @Override
    public CommentDTO findById(long id) throws ServiceException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found comment by ID: " + id);
            return new ServiceException("service.exception.not_found_comment_by_id");
        });
        return commentConverter.toDTO(comment);
    }

    @Override
    public Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage,
                                                long countAllElements, int page, int size) {
        return commentPagination.getPagination(elementsOnPage, countAllElements, page, size);
    }
}