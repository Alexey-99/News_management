package com.mjc.school.converter;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsConverterTest {
    @InjectMocks
    private NewsConverter newsConverter;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsTagRepository newsTagRepository;

    @Test
    void fromDTO() {
    }

    @Test
    void toDTO() {
    }
}