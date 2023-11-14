package com.mjc.school.service.author;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.Author;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private PaginationService<AuthorDTO> authorPagination;
    @Mock
    private PaginationService<AuthorIdWithAmountOfWrittenNewsDTO> amountOfWrittenNewsDTOPagination;
    private static AuthorDTO authorDTOTesting;
    private static AuthorDTO authorDTOExpected;
    private static AuthorDTO authorDTOActual;

    @Test
    void create_when_authorNotExistsByName() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);
        boolean actualResult = authorService.create(authorDTOTesting);
        assertTrue(actualResult);
    }

    @Test
    void create_when_authorExistsByName() {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.create(authorDTOTesting)
        );
        assertEquals("tag_dto.name.not_valid.exists_tag_by_name", exception.getMessage());
    }

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

    @Test
    void update_when_notFoundAuthorById() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.empty());
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));
        assertEquals("service.exception.not_found_authors_by_id", exception.getMessage());
    }

    @Test
    void update_when_foundAuthorByIdAndAuthorNamesEqual() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_notExistsAuthorByName() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);

        authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test_other").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_existsAuthorByName() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("author_dto.name.not_valid.already_exists", exceptionActual.getMessage());
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void countAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByPartOfName() {
    }

    @Test
    void countAllByPartOfName() {
    }

    @Test
    void findByNewsId() {
    }

    @Test
    void findAllAuthorsIdWithAmountOfWrittenNews() {
    }

    @Test
    void getPagination() {
    }

    @Test
    void getPaginationAuthorIdWithAmountOfWrittenNews() {
    }

    @AfterAll
    static void afterAll() {
        authorDTOTesting = null;
    }
}