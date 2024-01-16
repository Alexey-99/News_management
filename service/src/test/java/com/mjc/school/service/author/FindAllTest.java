package com.mjc.school.service.author;

import com.mjc.school.converter.AuthorConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findAllWithPages_when_foundAuthors_and_sortNameAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(1).name("Alex").build(),
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build());

        when(authorRepository.findAllByNameAsc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortNameDesc")
    void findAllWithPages_when_foundAuthors_and_sortNameDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(1).name("Alex").build(),
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build());

        when(authorRepository.findAllByNameDesc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortNameDesc() {
        return List.of(
                Arguments.of("name", "DESC"),
                Arguments.of("name", null),
                Arguments.of("name", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCountNewsDesc")
    void findAllWithPages_when_foundAuthors_and_sortCountNewsDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(1).name("Alex")
                        .news(List.of(
                                News.builder().id(1).build(),
                                News.builder().id(2).build()))
                        .build(),
                Author.builder().id(3).name("Bam")
                        .news(List.of(
                                News.builder().id(3).build()))
                        .build(),
                Author.builder().id(2).name("Sem").news(List.of()).build());

        when(authorRepository.findAllByCountNewsDesc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(
                Author.builder().id(1).name("Alex")
                        .news(List.of(
                                News.builder().id(1).build(),
                                News.builder().id(2).build()))
                        .build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").countNews(2).build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam")
                .news(List.of(
                        News.builder().id(3).build()))
                .build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").countNews(1).build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").news(List.of()).build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").countNews(0).build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").countNews(2).build(),
                AuthorDTO.builder().id(3).name("Bam").countNews(1).build(),
                AuthorDTO.builder().id(2).name("Sem").countNews(0).build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCountNewsDesc() {
        return List.of(
                Arguments.of("count_news", "DESC"),
                Arguments.of("count_news", null),
                Arguments.of("count_news", "type"));
    }

    @Test
    void findAllWithPages_when_foundAuthors_and_sortCountNewsAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "count_news";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(2).name("Sem").news(List.of()).build(),
                Author.builder().id(3).name("Bam")
                        .news(List.of(
                                News.builder().id(3).build()))
                        .build(),
                Author.builder().id(1).name("Alex")
                        .news(List.of(
                                News.builder().id(1).build(),
                                News.builder().id(2).build()))
                        .build());

        when(authorRepository.findAllByCountNewsAsc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(
                Author.builder().id(1).name("Alex")
                        .news(List.of(
                                News.builder().id(1).build(),
                                News.builder().id(2).build()))
                        .build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").countNews(2).build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam")
                .news(List.of(
                        News.builder().id(3).build()))
                .build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").countNews(1).build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").news(List.of()).build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").countNews(0).build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(2).name("Sem").countNews(0).build(),
                AuthorDTO.builder().id(3).name("Bam").countNews(1).build(),
                AuthorDTO.builder().id(1).name("Alex").countNews(2).build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findAllWithPages_when_foundAuthors_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build(),
                Author.builder().id(1).name("Alex").build());

        when(authorRepository.findAllByIdDesc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build(),
                AuthorDTO.builder().id(1).name("Alex").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdDesc() {
        return List.of(
                Arguments.of("id", "DESC"),
                Arguments.of("id", null),
                Arguments.of(null, "DESC"),
                Arguments.of(null, null),
                Arguments.of("field", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdAsc")
    void findAllWithPages_when_foundAuthors_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(1).name("Alex").build(),
                Author.builder().id(2).name("Sem").build(),
                Author.builder().id(3).name("Bam").build());

        when(authorRepository.findAllByIdAsc(anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(2).name("Sem").build(),
                AuthorDTO.builder().id(3).name("Bam").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @Test
    void findAllWithPages_when_notFoundAuthors() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";

        when(authorRepository.findAllByNameAsc(anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> authorService.findAll(page, size, sortField, sortType));
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
        long countAllAuthors = 3L;

        when(authorRepository.countAll()).thenReturn(countAllAuthors);

        long countAllActual = authorService.countAll();
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }
}