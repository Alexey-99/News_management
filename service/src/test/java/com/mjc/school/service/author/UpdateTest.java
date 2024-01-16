package com.mjc.school.service.author;

import com.mjc.school.converter.AuthorConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
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
class UpdateTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;

    @Test
    void update_when_notFoundAuthorById() {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("service.exception.not_found_authors_by_id", exception.getMessage());
    }

    @Test
    void update_when_foundAuthorByIdAndAuthorNamesEqual() throws ServiceBadRequestParameterException {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        AuthorDTO authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        AuthorDTO authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_notExistsAuthorByName() throws ServiceBadRequestParameterException {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);

        AuthorDTO authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test_other").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        AuthorDTO authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_existsAuthorByName() {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("author_dto.name.not_valid.already_exists", exceptionActual.getMessage());
    }
}