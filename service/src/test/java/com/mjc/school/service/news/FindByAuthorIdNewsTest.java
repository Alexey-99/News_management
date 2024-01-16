package com.mjc.school.service.news;

import com.mjc.school.converter.NewsConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByAuthorIdNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByAuthorId_when_notFoundNews() {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        when(newsRepository.findByAuthorIdByModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByAuthorId(authorId, page, size, sortField, sortType));
    }

    @Test
    void findByAuthorId_when_foundNews_and_sortModifiedDesc() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByAuthorIdByModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByAuthorId_when_foundNews_and_sortModifiedDesc_and_sortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;
        String sortField = null;

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByAuthorIdByModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByAuthorId_when_foundNews_and_sortModifiedAsc() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build()
        );
        when(newsRepository.findByAuthorIdByModifiedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByAuthorId_when_foundNews_and_sortModifiedAsc_and_sortFieldNull() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = null;

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build()
        );
        when(newsRepository.findByAuthorIdByModifiedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByAuthorId_when_foundNews_and_sortCreatedDesc() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByAuthorIdByCreatedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByAuthorId_when_foundNews_and_sortCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByAuthorIdByCreatedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByAuthorId_when_foundNews_and_sortCreatedAsc() throws ServiceNoContentException {
        long authorId = 1;

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        List<News> newsList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(Author.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByAuthorIdByCreatedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .author(Author.builder().id(authorId).build())
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .author(AuthorDTO.builder().id(authorId).build())
                        .created("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByAuthorId(authorId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void countAllNewsByAuthorId() {
        long authorId = 1;

        when(newsRepository.countAllNewsByAuthorId(anyLong())).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByAuthorId(authorId);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}