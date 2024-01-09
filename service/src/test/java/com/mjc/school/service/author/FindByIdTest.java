package com.mjc.school.service.author;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
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
class FindByIdTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;

    @Test
    void findById_when_foundAuthorById() throws ServiceNoContentException {
        long authorId = 1L;

        Author authorFromDB = Author.builder()
                .id(1).name("Alex").news(List.of())
                .build();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorFromDB));

        AuthorDTO authorDTOExpected = AuthorDTO.builder().id(1).name("Alex").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        AuthorDTO authorDTOActual = authorService.findById(authorId);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void findById_when_notFoundAuthorById() {
        long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(ServiceNoContentException.class,
                () -> authorService.findById(authorId));
    }
}