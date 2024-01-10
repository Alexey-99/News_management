package com.mjc.school.service.comment;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.handler.DateHandler;
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
class CreateCommentTest {
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

    @Test
    void create_when_newsNotFoundById() {
        long newsId = 2;
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.fromDTO(any(CommentDTO.class)))
                .thenReturn(Comment.builder()
                        .id(commentDTOTesting.getId())
                        .content(commentDTOTesting.getContent())
                        .newsId(commentDTOTesting.getNewsId())
                        .modified(commentDTOTesting.getModified())
                        .created(commentDTOTesting.getCreated())
                        .build());
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.create(commentDTOTesting));
        assertEquals("service.exception.not_exists_news_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void create_when_newsFoundById() throws ServiceBadRequestParameterException {
        long newsId = 2;
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        Comment commentConverted = Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build();
        when(commentConverter.fromDTO(any(CommentDTO.class)))
                .thenReturn(commentConverted);

        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(News.builder().id(newsId).build()));

        String currentDateTime = "date-time";
        when(dateHandler.getCurrentDate()).thenReturn(currentDateTime);

        commentConverted.setCreated(currentDateTime);
        commentConverted.setModified(currentDateTime);
        when(commentRepository.save(any(Comment.class))).thenReturn(commentConverted);

        CommentDTO commentDTOExpected = CommentDTO.builder()
                .id(commentConverted.getId())
                .content(commentConverted.getContent())
                .newsId(commentConverted.getNewsId())
                .modified(commentConverted.getModified())
                .created(commentConverted.getCreated())
                .build();

        when(commentConverter.toDTO(any(Comment.class))).thenReturn(commentDTOExpected);

        CommentDTO resultActual = commentService.create(commentDTOTesting);
        assertEquals(commentDTOExpected, resultActual);
    }

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
}