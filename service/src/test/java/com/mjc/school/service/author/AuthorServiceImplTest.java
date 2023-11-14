package com.mjc.school.service.author;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

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
    private static Author authorTesting;
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
    void update_when_notFoundAuthorById() {
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
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_existsAuthorByName() {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("author_dto.name.not_valid.already_exists", exceptionActual.getMessage());
    }

    @Test
    void findAllWithPages_when_foundAuthors() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());
        when(authorRepository.findAllList(PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Author.builder().id(1).name("Alex").build(),
                        Author.builder().id(3).name("Bam").build(),
                        Author.builder().id(2).name("Sem").build()));

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @Test
    void findAllWithPages_when_notFoundAuthors() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(authorRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findAll(page, size, sortField, sortType));

        assertEquals("service.exception.not_found_authors", exceptionActual.getMessage());
    }

    @Test
    void findAllAuthors() {
        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());
        when(authorRepository.findAll()).thenReturn(List.of(
                Author.builder().id(1).name("Alex").build(),
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build()));

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll();
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @Test
    void countAllAuthors() {
        when(authorRepository.countAll()).thenReturn(3L);

        long countAllActual = authorService.countAll();
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }

    @Test
    void findById_when_foundAuthorById() throws ServiceNoContentException {
        long authorId = 1L;

        Author authorFromDB = Author.builder()
                .id(1).name("Alex").news(List.of())
                .build();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorFromDB));

        authorDTOExpected = AuthorDTO.builder().id(1).name("Alex").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.findById(authorId);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void findById_when_notFoundAuthorById() {
        long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findById(authorId));

        assertEquals("service.exception.not_found_author_by_id", exceptionActual.getMessage());
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

    @Test
    void getOptionalSortField() {
    }

    @AfterAll
    static void afterAll() {
        authorDTOTesting = null;
    }
}