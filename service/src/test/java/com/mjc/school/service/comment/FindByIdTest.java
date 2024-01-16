package com.mjc.school.service.comment;

import com.mjc.school.converter.CommentConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Comment;
import com.mjc.school.repository.CommentRepository;
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
class FindByIdTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentConverter commentConverter;

    @Test
    void findById_when_notFoundCommentById() {
        long commentId = 1;
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceNoContentException.class, () -> commentService.findById(commentId));
    }

    @Test
    void findById_when_foundCommentById() throws ServiceNoContentException {
        long commentId = 1;
        Comment commentFromDB = Comment.builder().id(commentId).build();
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(commentFromDB));
        when(commentConverter.toDTO(any(Comment.class)))
                .thenReturn(CommentDTO.builder().id(commentId).build());
        CommentDTO commentDTOActual = commentService.findById(commentId);
        CommentDTO commentDTOExpected = CommentDTO.builder().id(commentId).build();
        assertEquals(commentDTOExpected, commentDTOActual);
    }
}