package com.mjc.school.service.tag;

import com.mjc.school.converter.TagConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import com.mjc.school.validation.dto.TagDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNewsIdTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByNewsId_when_notFoundTags() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        long newsId = 1;

        when(tagRepository.findByNewsIdSortNameAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findByNewsId(newsId, page, size, sortField, sortType));
    }

    @Test
    void findByNewsId_when_notFoundTags_and_withoutPage() {
        String sortType = "asc";
        long newsId = 1;

        when(tagRepository.findByNewsIdSortNameAsc(anyLong()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class, () -> tagService.findByNewsId(newsId, sortType));
    }

    @Test
    void findByNewsId_when_foundTags_and_sortNameAsc_and_withoutPage() throws ServiceNoContentException {
        String sortType = "ASC";
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build());

        when(tagRepository.findByNewsIdSortNameAsc(anyLong())).thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void findByNewsId_when_foundTags_and_sortNameAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build());

        when(tagRepository.findByNewsIdSortNameAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }


    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortNameDesc")
    void findByNewsId_when_foundTags_and_sortNameDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build());

        when(tagRepository.findByNewsIdSortNameDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortNameDesc() {
        return List.of(
                Arguments.of("name", "DESC"),
                Arguments.of("name", null));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortType_when_sortNameDesc")
    void findByNewsId_when_foundTags_and_sortNameDesc(String sortType) throws ServiceNoContentException {
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build());

        when(tagRepository.findByNewsIdSortNameDesc(anyLong()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortType_when_sortNameDesc() {
        return List.of(
                Arguments.of("DESC"),
                Arguments.of("type"));
    }

    @Test
    void findByNewsId_when_foundTags_and_sortNameDesc_and_sortTypeNull() throws ServiceNoContentException {
        long newsId = 1;

        String sortType = null;

        News news = News.builder().id(newsId).build();

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(NewsTag.builder()
                                .news(news)
                                .build()))
                        .build());

        when(tagRepository.findByNewsIdSortNameDesc(anyLong()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCountNewsDesc")
    void findByNewsId_when_foundTags_and_sortCountNewsDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(4)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 1).build())
                                        .build()))
                        .build());

        when(tagRepository.findAllSortCountNewsDesc())
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build(),
                        NewsTag.builder().news(news).build(),
                        NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCountNewsDesc() {
        return List.of(
                Arguments.of("count_news", "DESC"),
                Arguments.of("count_news", null));
    }

    @Test
    void findByNewsId_when_foundTags_and_sortCountNewsAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        long newsId = 1;
        String sortField = "count_news";
        String sortType = "ASC";

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(4)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 1).build())
                                        .build()))
                        .build());

        when(tagRepository.findAllSortCountNewsAsc())
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build(),
                        NewsTag.builder().news(news).build(),
                        NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }


    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findByNewsId_when_foundTags_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 2).build())
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build());

        when(tagRepository.findByNewsIdSortIdDesc(anyLong(), anyInt(), anyInt()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(
                        NewsTag.builder()
                                .news(news)
                                .build(),
                        NewsTag.builder()
                                .news(News.builder().id(newsId + 1).build())
                                .build(),
                        NewsTag.builder()
                                .news(News.builder().id(newsId + 2).build())
                                .build()))
                .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name")
                                .countNews(3)
                                .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdDesc() {
        return List.of(
                Arguments.of("id", "DESC"),
                Arguments.of("id", null),
                Arguments.of(null, null),
                Arguments.of("field", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdAsc")
    void findByNewsId_when_foundTags_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        List<Tag> tagListByNewsId = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(news)
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(newsId + 2).build())
                                        .build()))
                        .build());

        when(tagRepository.findByNewsIdSortIdAsc(anyLong(), anyInt(), anyInt()))
                .thenReturn(tagListByNewsId);

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(
                        NewsTag.builder()
                                .news(news)
                                .build(),
                        NewsTag.builder()
                                .news(News.builder().id(newsId + 1).build())
                                .build(),
                        NewsTag.builder()
                                .news(News.builder().id(newsId + 2).build())
                                .build()))
                .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name")
                                .countNews(3)
                                .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(3)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @Test
    void countAllByNewsId() {
        long newsId = 1;
        long countAllByNewsId = 2;

        when(tagRepository.countAllByNewsId(newsId)).thenReturn(countAllByNewsId);

        long countAllByNewsIdActual = tagService.countAllByNewsId(newsId);
        long countAllByNewsIdExpected = 2;

        assertEquals(countAllByNewsIdExpected, countAllByNewsIdActual);
    }
}