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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByPartOfNameTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByPartOfNameWithPages_when_foundAuthors_and_sortNameAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);


        List<Author> authorList = List.of(
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build());

        when(authorRepository.findByPartOfNameByNameAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortNameDesc")
    void findByPartOfNameWithPages_when_foundAuthors_and_sortNameDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);


        List<Author> authorList = List.of(
                Author.builder().id(2).name("Sem").build(),
                Author.builder().id(3).name("Bam").build());

        when(authorRepository.findByPartOfNameByNameDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(2).name("Sem").build(),
                AuthorDTO.builder().id(3).name("Bam").build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortNameDesc() {
        return List.of(
                Arguments.of("name", "DESC"),
                Arguments.of("name", null),
                Arguments.of("name", "type"));
    }

    @Test
    void findByPartOfNameWithPages_when_foundAuthors_and_sortCountNewsAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "count_news";
        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(3).name("Bam")
                        .news(List.of(News.builder().id(1).build()))
                        .build(),
                Author.builder().id(2).name("Sem")
                        .news(List.of(
                                News.builder().id(2).build(),
                                News.builder().id(3).build(),
                                News.builder().id(4).build()))
                        .build());

        when(authorRepository.findByPartOfNameByCountNewsAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(
                Author.builder().id(3).name("Bam")
                        .news(List.of(News.builder().id(1).build())).build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").countNews(1).build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").countNews(3).build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(3).name("Bam").countNews(1).build(),
                AuthorDTO.builder().id(2).name("Sem").countNews(3).build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCountNewsDesc")
    void findByPartOfNameWithPages_when_foundAuthors_and_sortCountNewsDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Author> authorList = List.of(
                Author.builder().id(2).name("Sem")
                        .news(List.of(
                                News.builder().id(2).build(),
                                News.builder().id(3).build(),
                                News.builder().id(4).build()))
                        .build(),
                Author.builder().id(3).name("Bam")
                        .news(List.of(News.builder().id(1).build()))
                        .build());

        when(authorRepository.findByPartOfNameByCountNewsDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(
                Author.builder().id(3).name("Bam")
                        .news(List.of(News.builder().id(1).build())).build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").countNews(1).build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").countNews(3).build());


        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(2).name("Sem").countNews(3).build(),
                AuthorDTO.builder().id(3).name("Bam").countNews(1).build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCountNewsDesc() {
        return List.of(
                Arguments.of("count_news", "DESC"),
                Arguments.of("count_news", null),
                Arguments.of("count_news", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdAsc")
    void findByPartOfNameWithPages_when_foundAuthors_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);


        List<Author> authorList = List.of(
                Author.builder().id(2).name("Sem").build(),
                Author.builder().id(3).name("Bam").build());

        when(authorRepository.findByPartOfNameByIdAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(2).name("Sem").build(),
                AuthorDTO.builder().id(3).name("Bam").build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findByPartOfNameWithPages_when_foundAuthors_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String partOfName = "m";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);


        List<Author> authorList = List.of(
                Author.builder().id(3).name("Sem").build(),
                Author.builder().id(2).name("Bam").build());

        when(authorRepository.findByPartOfNameByIdDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(authorList);

        when(authorConverter.toDTO(Author.builder().id(2).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Sem").build());

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(3).name("Sem").build(),
                AuthorDTO.builder().id(2).name("Bam").build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
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


    @Test
    void findByPartOfNameWithPages_when_notFoundAuthors() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "m";

        when(authorRepository.findByPartOfNameByNameAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> authorService.findByPartOfName(partOfName, page, size, sortField, sortType));
    }

    @Test
    void countAllByPartOfName() {
        long countAllByPartOfName = 2L;
        String partOfName = "m";

        when(authorRepository.countAllByPartOfName("%" + partOfName + "%")).thenReturn(countAllByPartOfName);

        long countAllActual = authorService.countAllByPartOfName(partOfName);
        long countAllExpected = 2L;

        assertEquals(countAllExpected, countAllActual);
    }
}