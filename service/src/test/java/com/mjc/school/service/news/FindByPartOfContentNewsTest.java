package com.mjc.school.service.news;

import com.mjc.school.converter.NewsConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByPartOfContentNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByPartOfContent_when_notFoundNews() {
        String partOfContent = "partOfContent";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        when(newsRepository.findByPartOfContentByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType));
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByModifiedDesc() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfContentByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByModifiedDesc_andSortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfContentByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByModifiedAsc() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByPartOfContentByModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByModifiedAsc_and_sortFieldNull() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByPartOfContentByModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByCreatedDesc() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfContentByCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfContentByCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfContent_when_foundNews_and_sortByCreatedAsc() throws ServiceNoContentException {
        String partOfContent = "partOfContent";
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
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByPartOfContentByCreatedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("content partOfContent 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("content 3 partOfContent")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("partOfContent content 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("partOfContent content 2")
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("content 3 partOfContent")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("content partOfContent 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfContent(partOfContent, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void countAllNewsByPartOfContent() {
        String partOfContent = "partOfContent";

        when(newsRepository.countAllNewsByPartOfContent(anyString())).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByPartOfContent(partOfContent);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}