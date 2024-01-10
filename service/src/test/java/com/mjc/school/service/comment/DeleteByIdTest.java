package com.mjc.school.service.comment;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.comment.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteByIdTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;

    @Test
    void deleteById_when_commentExistsById() {
        long commentId = 1;
        when(commentRepository.existsById(anyLong())).thenReturn(true);
        boolean actualResult = commentService.deleteById(commentId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_commentNotExistsById() {
        long commentId = 1;
        when(commentRepository.existsById(anyLong())).thenReturn(false);
        boolean actualResult = commentService.deleteById(commentId);
        assertTrue(actualResult);
    }
}