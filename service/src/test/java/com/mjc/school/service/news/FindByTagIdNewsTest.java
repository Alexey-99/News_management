package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByTagIdNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByTagId_when_notFoundNewsByTagId() {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(newsRepository.findByTagIdModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByTagId(tagId, page, size, sortField, sortType));
    }

    @Test
    void findByTagId_when_foundNews_and_sortModifiedDesc() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagIdModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortModifiedDesc_and_sortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = null;
        String sortField = null;

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagIdModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortModifiedDesc_and_sortTypeNull() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = null;
        String sortField = "modified";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagIdModifiedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortModifiedAsc() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "modified";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagIdModifiedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortModifiedAsc_andSortFieldNull() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = null;

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagIdModifiedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByTagId_when_foundNews_and_sortCreatedDesc() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "created";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagIdCreatedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(
                        NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = null;
        String sortField = "created";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagIdCreatedDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagId_when_foundNews_and_sortCreatedAsc() throws ServiceNoContentException {
        long tagId = 1;

        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "created";

        List<News> newsFindByTagIdList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(Tag.builder().id(tagId).build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagIdCreatedAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagIdList);

        when(newsConverter.toDTO(News.builder()
                .id(1)
                .content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(Tag.builder().id(tagId).build())
                                .build()))
                .created("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build());
        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(1)
                        .created("2023-10-20T16:05:38.685")
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagId(tagId, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void countAllNewsByTagId() {
        long tagId = 1;

        when(newsRepository.countAllNewsByTagId(anyLong())).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByTagId(tagId);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}