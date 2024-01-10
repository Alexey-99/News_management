package com.mjc.school.service.comment;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
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
class DeleteByNewsIdTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private NewsRepository newsRepository;
    @Mock// THIS MOCK USING IN THIS TEST. DON'T DELETE IT
    private CommentRepository commentRepository;

    @Test
    void deleteByNewsId_when_newsExistsById() {
        long newsId = 1;
        when(newsRepository.existsById(anyLong())).thenReturn(true);
        boolean actualResult = commentService.deleteByNewsId(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByNewsId_when_newsNotExistsById() {
        long newsId = 1;
        when(newsRepository.existsById(anyLong())).thenReturn(false);
        boolean actualResult = commentService.deleteByNewsId(newsId);
        assertTrue(actualResult);
    }
}