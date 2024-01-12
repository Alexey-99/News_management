package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.comment.impl.sort.CommentSortField;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.NewsDTO;
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
class FindByPartOfAuthorNameNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByPartOfAuthorName_when_notFoundNews() {
        String partOfAuthorName = "part";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(newsRepository.findByPartOfAuthorNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByPartOfAuthorName(partOfAuthorName, page, size, sortField, sortType));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortModifiedDesc")
    void findByPartOfAuthorName_when_foundNews_sortModifiedDesc(String sortField, String sortType) throws ServiceNoContentException {
        String partOfAuthorName = "part";

        int page = 1;
        int size = 5;

        List<News> newsFindByPartOfAuthorNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfAuthorNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByPartOfAuthorNameList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(1).name("Alpartex").build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(3).name("partBam").build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(2).name("Sempart").build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfAuthorName(partOfAuthorName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortModifiedDesc() {
        return List.of(
                Arguments.of(null, null),
                Arguments.of(null, "DESC"),
                Arguments.of("modified", "DESC")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortModifiedAsc")
    void findByPartOfAuthorName_when_foundNews_sortModifiedAsc(String sortField, String sortType) throws ServiceNoContentException {
        String partOfAuthorName = "part";

        int page = 1;
        int size = 5;

        List<News> newsFindByPartOfAuthorNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfAuthorNameModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByPartOfAuthorNameList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(1).name("Alpartex").build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(3).name("partBam").build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(2).name("Sempart").build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfAuthorName(partOfAuthorName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortModifiedAsc() {
        return List.of(
                Arguments.of(null, "ASC"),
                Arguments.of("modified", "ASC")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCreatedDesc")
    void findByPartOfAuthorName_when_foundNews_and_sortCreatedDesc(String sortField, String sortType) throws ServiceNoContentException {
        String partOfAuthorName = "part";

        int page = 1;
        int size = 5;

        List<News> newsFindByPartOfAuthorNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfAuthorNameCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByPartOfAuthorNameList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(1).name("Alpartex").build())
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(3).name("partBam").build())
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(2).name("Sempart").build())
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfAuthorName(partOfAuthorName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCreatedDesc() {
        return List.of(
                Arguments.of("created", null),
                Arguments.of("created", "DESC")
        );
    }


    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCreatedAsc")
    void findByPartOfAuthorName_when_foundNews_sortCreatedAsc(String sortField, String sortType) throws ServiceNoContentException {
        String partOfAuthorName = "part";

        int page = 1;
        int size = 5;

        List<News> newsFindByPartOfAuthorNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfAuthorNameCreatedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByPartOfAuthorNameList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(1).name("Alpartex").build())
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(3).name("partBam").build())
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(2).name("Sempart").build())
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(1).name("Alpartex").build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(3).name("partBam").build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(2).name("Sempart").build())
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfAuthorName(partOfAuthorName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCreatedAsc() {
        return List.of(Arguments.of("created", "ASC"));
    }

    @Test
    void countAllNewsByPartOfAuthorName() {
        String partOfAuthorName = "part";

        when(newsRepository.countAllNewsByPartOfAuthorName(anyString())).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByPartOfAuthorName(partOfAuthorName);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}