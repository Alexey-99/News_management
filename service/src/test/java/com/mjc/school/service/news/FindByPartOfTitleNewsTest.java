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
class FindByPartOfTitleNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByPartOfTitle_when_notFoundNews() {
        String partOfTitle = "partOfTitle";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(numberFirstElement);

        when(newsRepository.findByPartOfTitleByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType));
    }

    @Test
    void findByPartOfTitle_when_foundNews_and_sortModifiedDesc() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfTitleByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .modified("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .modified("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByPartOfTitle_when_foundNews_and_sortModifiedDesc_and_sortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfTitleByModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .modified("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .modified("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByPartOfTitle_when_foundNews_and_sortModifiedAsc() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByPartOfTitleByModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .modified("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .modified("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByPartOfTitle_when_foundNews_and_sortCreatedDesc() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfTitleByCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .created("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .created("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(2)
                                .title("partOfTitle title 2")
                                .created("2023-10-20T16:05:25.413")
                                .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByPartOfTitle_when_foundNews_and_sortCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByPartOfTitleByCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .created("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .created("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(2)
                                .title("partOfTitle title 2")
                                .created("2023-10-20T16:05:25.413")
                                .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByPartOfTitle_when_foundNews_and_sortCreatedAsc() throws ServiceNoContentException {
        String partOfTitle = "partOfTitle";

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
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByPartOfTitleByCreatedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsList);

        when(newsConverter.toDTO(
                News.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(1)
                                .title("title partOfTitle 1")
                                .created("2023-10-20T16:05:38.685")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(3)
                                .title("title 3 partOfTitle")
                                .created("2023-10-20T16:05:32.413")
                                .build());
        when(newsConverter.toDTO(
                News.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build()))
                .thenReturn(
                        NewsDTO.builder()
                                .id(2)
                                .title("partOfTitle title 2")
                                .created("2023-10-20T16:05:25.413")
                                .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .title("partOfTitle title 2")
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .title("title 3 partOfTitle")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .title("title partOfTitle 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByPartOfTitle(partOfTitle, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void countAllNewsByPartOfTitle() {
        String partOfTitle = "partOfTitle";

        when(newsRepository.countAllNewsByPartOfTitle(anyString())).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByPartOfTitle(partOfTitle);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}