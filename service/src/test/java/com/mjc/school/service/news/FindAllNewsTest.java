package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findAllWithPages_when_notFoundNews() {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        when(newsRepository.findAllByModifiedDesc(anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findAll(page, size, sortField, sortType));
    }

    @Test
    void findAllWithPages_when_foundNews_and_sortModifiedDesc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAllByModifiedDesc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllWithPages_when_foundNews_and_sortModifiedDesc_and_sortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = null;
        String sortField = null;

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAllByModifiedDesc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllWithPages_when_foundNews_and_sortModifiedAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(newsRepository.findAllByModifiedAsc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),

                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findAllWithPages_when_foundNews_and_sortCreatedDesc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAllByCreatedDesc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllWithPages_when_foundNews_and_sortCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = null;
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAllByCreatedDesc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllWithPages_when_foundNews_and_sortCreatedAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                News.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                News.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(newsRepository.findAllByCreatedAsc(anyInt(), anyInt()))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findAllNews_when_notFoundNews() {
        when(newsRepository.findAll()).thenReturn(List.of());
        List<NewsDTO> newsDTOListExpected = List.of();
        List<NewsDTO> newsDTOListActual = newsService.findAll();
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllNews_when_foundNews() {
        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAll()).thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(newsConverter.toDTO(News.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(newsConverter.toDTO(News.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListActual = newsService.findAll();
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void countAllNews() {
        when(newsRepository.countAllNews()).thenReturn(3L);
        long countAllNewsExpected = 3;
        long countAllNewsActual = newsService.countAllNews();
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}
