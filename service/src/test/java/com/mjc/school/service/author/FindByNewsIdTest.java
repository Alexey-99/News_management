package com.mjc.school.service.author;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.validation.dto.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNewsIdTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;

    @Test
    void findByNewsId_when_foundAuthors() throws ServiceNoContentException {
        long newsId = 1L;

        Author authorFromDB = Author.builder()
                .id(1L)
                .name("author name test")
                .news(List.of(News.builder()
                        .id(1L)
                        .build()))
                .build();
        when(authorRepository.findByNewsId(newsId))
                .thenReturn(Optional.of(authorFromDB));

        AuthorDTO authorDTOExpected = AuthorDTO.builder()
                .id(1L)
                .name("author name test")
                .countNews(1)
                .build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        AuthorDTO authorDTOActual = authorService.findByNewsId(newsId);
        assertEquals(authorDTOExpected, authorDTOActual);

    }

    @Test
    void findByNewsId_when_notFoundAuthors() {
        long newsId = 1L;
        when(authorRepository.findByNewsId(newsId)).thenReturn(Optional.empty());
        assertThrows(ServiceNoContentException.class, () -> authorService.findByNewsId(newsId));
    }
}