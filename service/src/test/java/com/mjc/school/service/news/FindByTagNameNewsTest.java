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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByTagNameNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortModifiedDesc() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortModifiedDesc_and_sortFieldNull_and_sortTypeNull() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;
        String sortField = null;

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortModifiedDesc_and_sortTypeNull() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_notFoundNewsByTagName() {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(newsRepository.findByTagNameModifiedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findByTagName(tagName, page, size, sortField, sortType));
    }

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortModifiedAsc() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagNameModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByTagName_when_foundNewsByTagName_and_sortModifiedAsc_and_sortFieldNull() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = null;

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagNameModifiedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void findByTagName_when_foundNewsByTagName_and_sortCreatedDesc() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagNameCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .created("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortCreatedDesc_and_sortTypeNull() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagNameCreatedDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(
                News.builder().id(1).content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .created("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_foundNewsByTagName_and_sortCreatedAsc() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "created";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt()))
                .thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:25.413")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .created("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsRepository.findByTagNameCreatedAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .created("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .created("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
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
                        .created("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }


    @Test
    void countAllNewsByTagName() {
        String tagName = "tag_name";

        when(newsRepository.countAllNewsByTagName(anyString())).thenReturn(3L);
        long countAllNewsExpected = 3;
        long countAllNewsActual = newsService.countAllNewsByTagName(tagName);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }
}