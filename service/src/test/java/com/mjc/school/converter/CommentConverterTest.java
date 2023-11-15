package com.mjc.school.converter;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.validation.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommentConverterTest {
    @InjectMocks
    private CommentConverter commentConverter;

    @Test
    void fromDTO() {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        Comment commentExpected = Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .created(commentDTOTesting.getCreated())
                .modified(commentDTOTesting.getModified())
                .build();
        Comment commentActual = commentConverter.fromDTO(commentDTOTesting);
        assertEquals(commentExpected, commentActual);
    }

    @Test
    void toDTO() {
        Comment commentTesting = Comment.builder()
                .id(1)
                .content("comment content test")
                .news(News.builder().id(2).build())
                .created("created date-time")
                .modified("modified date-time")
                .build();
        CommentDTO commentDTOExpected = CommentDTO.builder()
                .id(commentTesting.getId())
                .content(commentTesting.getContent())
                .newsId(commentTesting.getNews().getId())
                .created(commentTesting.getCreated())
                .modified(commentTesting.getModified())
                .build();
        CommentDTO commentDTOActual = commentConverter.toDTO(commentTesting);
        assertEquals(commentDTOExpected, commentDTOActual);
    }
}