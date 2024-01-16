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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByPartOfNameTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findByPartOfName_when_foundTags_and_sortNameAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(1).name("A_tag_name_choose").build(),
                Tag.builder().id(3).name("C_tag_name_choose").build());

        when(tagRepository.findByPartOfNameSortNameAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name_choose").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name_choose").build());


        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name_choose").build(),
                TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortNameDesc")
    void findByPartOfName_when_foundTags_and_sortNameDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(3).name("C_tag_name_choose").build(),
                Tag.builder().id(1).name("A_tag_name_choose").build());

        when(tagRepository.findByPartOfNameSortNameDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name_choose").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(3).name("C_tag_name_choose").build(),
                TagDTO.builder().id(1).name("A_tag_name_choose").build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortNameDesc() {
        return List.of(
                Arguments.of("name", "DESC"),
                Arguments.of("name", null),
                Arguments.of("name", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCountNewsDesc")
    void findByPartOfName_when_foundTags_and_sortCountNewsDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(2).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(3).build())
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .news(List.of())
                        .build());

        when(tagRepository.findByPartOfNameSortCountNewsDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .news(List.of())
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(1)
                                .name("A_tag_name_choose")
                                .countNews(0)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(2).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(3).build())
                                        .build()))
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name_choose")
                                .countNews(3)
                                .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .countNews(3)
                        .build(),
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .countNews(0)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCountNewsDesc() {
        return List.of(
                Arguments.of("count_news", "DESC"),
                Arguments.of("count_news", null),
                Arguments.of("count_news", "type"));
    }

    @Test
    void findByPartOfName_when_foundTags_and_sortCountNewsAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "count_news";

        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .news(List.of())
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(2).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(3).build())
                                        .build()))
                        .build());

        when(tagRepository.findByPartOfNameSortCountNewsAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .news(List.of())
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(1)
                                .name("A_tag_name_choose")
                                .countNews(0)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(2).build())
                                        .build(),
                                NewsTag.builder()
                                        .news(News.builder().id(3).build())
                                        .build()))
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name_choose")
                                .countNews(3)
                                .build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name_choose")
                        .countNews(0)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name_choose")
                        .countNews(3)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findByPartOfName_when_foundTags_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(3).name("C_tag_name_choose").build(),
                Tag.builder().id(1).name("A_tag_name_choose").build());

        when(tagRepository.findByPartOfNameSortIdDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name_choose").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(3).name("C_tag_name_choose").build(),
                TagDTO.builder().id(1).name("A_tag_name_choose").build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
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
    void findByPartOfName_when_foundTags_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String partOfName = "name_choose";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(1).name("A_tag_name_choose").build(),
                Tag.builder().id(3).name("C_tag_name_choose").build());

        when(tagRepository.findByPartOfNameSortIdAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name_choose").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name_choose").build(),
                TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @Test
    void findByPartOfName_when_notFoundTags() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "name_choose";

        when(tagRepository.findByPartOfNameSortNameAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findByPartOfName(partOfName, page, size, sortField, sortType));
    }

    @Test
    void countAllByPartOfName() {
        String partOfName = "name_choose";
        long countAllByPartOfName = 2;

        when(tagRepository.countAllByPartOfName("%" + partOfName + "%"))
                .thenReturn(countAllByPartOfName);

        long countAllByPartOfNameActual = tagService.countAllByPartOfName(partOfName);
        long countAllByPartOfNameExpected = 2;

        assertEquals(countAllByPartOfNameExpected, countAllByPartOfNameActual);
    }
}