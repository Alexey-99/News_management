package com.mjc.school.service.author;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteByIdTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    void deleteById_when_authorExistsById() {
        long authorId = 1;
        when(authorRepository.existsById(authorId)).thenReturn(true);
        boolean actualResult = authorService.deleteById(1);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_authorNotExistsById() {
        long authorId = 1;
        when(authorRepository.existsById(authorId)).thenReturn(false);
        boolean actualResult = authorService.deleteById(authorId);
        assertTrue(actualResult);
    }
}