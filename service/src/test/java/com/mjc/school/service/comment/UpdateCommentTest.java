package com.mjc.school.service.comment;

import com.mjc.school.converter.CommentConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.util.DateFormatter;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.comment.impl.CommentServiceImpl;
import com.mjc.school.validation.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCommentTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentConverter commentConverter;
    @Mock
    private DateFormatter dateHandler;

    @Test
    void update_when_notFoundCommentById() {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.update(commentDTOTesting));
        assertEquals("service.exception.not_found_comment_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void update_when_foundCommentById_and_notFoundNewsById() {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(Comment.builder()
                        .id(commentDTOTesting.getId())
                        .content(commentDTOTesting.getContent())
                        .newsId(commentDTOTesting.getNewsId())
                        .modified(commentDTOTesting.getModified())
                        .created(commentDTOTesting.getCreated())
                        .build()));
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.update(commentDTOTesting));
        assertEquals("service.exception.not_exists_news_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void update_when_foundCommentById_and_foundNewsById() throws ServiceBadRequestParameterException {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();

        Comment commentFromDB = Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .news(News.builder().id(3).build())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(commentFromDB));

        when(dateHandler.getCurrentDate()).thenReturn("date-time");

        News newsFromDB = News.builder().id(commentDTOTesting.getNewsId()).build();
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(newsFromDB));

        commentFromDB.setNews(newsFromDB);
        CommentDTO commentDTOExpected = CommentDTO.builder()
                .id(1)
                .content("comment content updated test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.toDTO(any(Comment.class)))
                .thenReturn(commentDTOExpected);

        CommentDTO commentDTOActual = commentService.update(commentDTOTesting);
        assertEquals(commentDTOExpected, commentDTOActual);
    }
}