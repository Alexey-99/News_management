package com.mjc.school.service.comment.impl;

import com.mjc.school.converter.CommentConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Comment;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.util.DateFormatter;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.comment.impl.sort.CommentSortField.ID;
import static com.mjc.school.service.news.impl.sort.NewsSortField.CREATED;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Log4j2
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentConverter commentConverter;
    private final DateFormatter dateHandler;
    private final PaginationService paginationService;

    @Transactional
    @Override
    public CommentDTO create(CommentDTO commentDTO) throws ServiceBadRequestParameterException {
        Comment comment = commentConverter.fromDTO(commentDTO);
        comment.setNews(newsRepository.findById(comment.getNewsId()).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + commentDTO.getNewsId());
            return new ServiceBadRequestParameterException("service.exception.not_exists_news_by_id");
        }));
        comment.setCreated(dateHandler.getCurrentDate());
        comment.setModified(dateHandler.getCurrentDate());
        return commentConverter.toDTO(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDTO update(CommentDTO commentDTO) throws ServiceBadRequestParameterException {
        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found comment by ID: " + commentDTO.getId());
            return new ServiceBadRequestParameterException("service.exception.not_found_comment_by_id");
        });
        comment.setContent(commentDTO.getContent());
        comment.setNews(newsRepository.findById(commentDTO.getNewsId()).orElseThrow(() -> {
            log.log(WARN, "Not found news by ID: " + commentDTO.getNewsId());
            return new ServiceBadRequestParameterException("service.exception.not_exists_news_by_id");
        }));
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
                                    String sortField, String sortType) throws ServiceNoContentException {
        List<Comment> commentList;
        if (sortField != null && sortField.equalsIgnoreCase(CREATED.name())) {
            commentList = findAllSortCreated(sortType, page, size);
        } else if (sortField != null && sortField.equalsIgnoreCase(ID.name())) {
            commentList = findAllSortId(sortType, page, size);
        } else {
            commentList = findAllSortModified(sortType, page, size);
        }
        if (!commentList.isEmpty()) {
            return commentList.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found comments");
            throw new ServiceNoContentException();
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
    public List<CommentDTO> findByNewsId(long newsId,
                                         int page, int size,
                                         String sortField, String sortType) throws ServiceNoContentException {
        List<Comment> commentList;
        if (sortField != null && sortField.equalsIgnoreCase(CREATED.name())) {
            commentList = findByNewsIdSortCreated(newsId, sortType, page, size);
        } else if (sortField != null && sortField.equalsIgnoreCase(ID.name())) {
            commentList = findByNewsIdSortId(newsId, sortType, page, size);
        } else {
            commentList = findByNewsIdSortModified(newsId, sortType, page, size);
        }
        if (!commentList.isEmpty()) {
            return commentList.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(ERROR, "Not found comments by news ID: " + newsId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public List<CommentDTO> findByNewsId(long newsId, String sortType) throws ServiceNoContentException {
        List<Comment> commentList = findByNewsIdSortModified(newsId, sortType);
        if (!commentList.isEmpty()) {
            return commentList.stream()
                    .map(commentConverter::toDTO)
                    .toList();
        } else {
            log.log(ERROR, "Not found comments by news ID: " + newsId);
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllCommentsByNewsId(long newsId) {
        return commentRepository.countAllCommentsByNewsId(newsId);
    }

    @Override
    public CommentDTO findById(long id) throws ServiceNoContentException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found comment by ID: " + id);
            return new ServiceNoContentException();
        });
        return commentConverter.toDTO(comment);
    }

    @Override
    public Pagination<CommentDTO> getPagination(List<CommentDTO> elementsOnPage, long countAllElements, int page, int size) {
        return Pagination
                .<CommentDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .countAllEntity(countAllElements)
                .numberPage(page)
                .maxNumberPage(paginationService.calcMaxNumberPage(countAllElements, size))
                .build();
    }

    private List<Comment> findAllSortCreated(String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findAllSortCreatedAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findAllSortCreatedDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findAllSortId(String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findAllSortIdAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findAllSortIdDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findAllSortModified(String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findAllSortModifiedAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findAllSortModifiedDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findByNewsIdSortCreated(long newsId, String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findByNewsIdSortCreatedAsc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findByNewsIdSortCreatedDesc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findByNewsIdSortId(long newsId, String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findByNewsIdSortIdAsc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findByNewsIdSortIdDesc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findByNewsIdSortModified(long newsId, String sortType, int page, int size) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findByNewsIdSortModifiedAsc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            commentList = commentRepository.findByNewsIdSortModifiedDesc(newsId,
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return commentList;
    }

    private List<Comment> findByNewsIdSortModified(long newsId, String sortType) {
        List<Comment> commentList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            commentList = commentRepository.findByNewsIdSortModifiedAsc(newsId);
        } else {
            commentList = commentRepository.findByNewsIdSortModifiedDesc(newsId);
        }
        return commentList;
    }
}