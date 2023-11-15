package com.mjc.school.service;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.comment.impl.CommentServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentConverter commentConverter;
    @Mock
    private DateHandler dateHandler;
    @Mock
    private PaginationService paginationService;
    private static CommentDTO commentDTOTesting;

    @Test
    void create_when_newsNotFoundById() {
        long newsId = 2;
        commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.fromDTO(commentDTOTesting)).thenReturn(Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build());
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.create(commentDTOTesting));
        assertEquals("service.exception.not_exists_news_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void create_when_newsFoundById() throws ServiceBadRequestParameterException {
        long newsId = 2;
        commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.fromDTO(commentDTOTesting)).thenReturn(Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build());
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.of(News.builder().id(newsId).build()));

        boolean resultActual = commentService.create(commentDTOTesting);

        assertTrue(resultActual);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteByNewsId() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void countAllComments() {
    }

    @Test
    void findByNewsId() {
    }

    @Test
    void countAllCommentsByNewsId() {
    }

    @Test
    void findById() {
    }

    @Test
    void getPagination() {
    }

    @Test
    void getOptionalSortField() {
    }
}