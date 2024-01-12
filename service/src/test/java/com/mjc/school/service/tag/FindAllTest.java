package com.mjc.school.service.tag;

import com.mjc.school.converter.impl.TagConverter;
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
class FindAllTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void findAllWithPages_when_foundTags_and_sortNameAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(1).name("A_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build(),
                Tag.builder().id(3).name("C_tag_name").build());

        when(tagRepository.findAllSortNameAsc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());


        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortNameDesc")
    void findAllWithPages_when_foundTags_and_sortNameDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(3).name("C_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build(),
                Tag.builder().id(1).name("A_tag_name").build());

        when(tagRepository.findAllSortNameDesc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(1).name("A_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
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
    void findAllWithPages_when_foundTags_and_sortCountNewsDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
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
                        .id(2)
                        .name("B_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of())
                        .build());

        when(tagRepository.findAllSortCountNewsDesc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
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
                                .id(1)
                                .name("A_tag_name")
                                .countNews(3)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of())
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name")
                                .countNews(0)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(2)
                        .name("B_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build()))
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(2)
                                .name("B_tag_name")
                                .countNews(1)
                                .build());


        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").countNews(3).build(),
                TagDTO.builder().id(2).name("B_tag_name").countNews(1).build(),
                TagDTO.builder().id(3).name("C_tag_name").countNews(0).build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCountNewsDesc() {
        return List.of(
                Arguments.of("count_news", "DESC"),
                Arguments.of("count_news", null),
                Arguments.of("count_news", "type"));
    }

    @Test
    void findAllWithPages_when_foundTags_and_sortCountNewsAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "count_news";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of())
                        .build(),
                Tag.builder()
                        .id(2)
                        .name("B_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build()))
                        .build(),
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
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

        when(tagRepository.findAllSortCountNewsAsc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(
                Tag.builder()
                        .id(1)
                        .name("A_tag_name")
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
                                .id(1)
                                .name("A_tag_name")
                                .countNews(3)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(3)
                        .name("C_tag_name")
                        .news(List.of())
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(3)
                                .name("C_tag_name")
                                .countNews(0)
                                .build());
        when(tagConverter.toDTO(
                Tag.builder()
                        .id(2)
                        .name("B_tag_name")
                        .news(List.of(
                                NewsTag.builder()
                                        .news(News.builder().id(1).build())
                                        .build()))
                        .build()))
                .thenReturn(
                        TagDTO.builder()
                                .id(2)
                                .name("B_tag_name")
                                .countNews(1)
                                .build());


        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(3).name("C_tag_name").countNews(0).build(),
                TagDTO.builder().id(2).name("B_tag_name").countNews(1).build(),
                TagDTO.builder().id(1).name("A_tag_name").countNews(3).build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }


    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findAllWithPages_when_foundTags_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(3).name("C_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build(),
                Tag.builder().id(1).name("A_tag_name").build());

        when(tagRepository.findAllSortIdDesc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(1).name("A_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
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
    void findAllWithPages_when_foundTags_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Tag> tagList = List.of(
                Tag.builder().id(1).name("A_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build(),
                Tag.builder().id(3).name("C_tag_name").build());

        when(tagRepository.findAllSortIdAsc(anyInt(), anyInt()))
                .thenReturn(tagList);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());


        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @Test
    void findAllWithPages_when_notFoundTags() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);
        when(tagRepository.findAllSortNameAsc(anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findAll(page, size, sortField, sortType));
    }

    @Test
    void findAll_when_foundTags() throws ServiceNoContentException {
        String sortType = "ASC";

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());
        when(tagRepository.findAllSortNameAsc()).thenReturn(List.of(
                Tag.builder().id(1).name("A_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build(),
                Tag.builder().id(3).name("C_tag_name").build()));

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void findAll_when_notFoundTags() {
        String sortType = "ASC";
        when(tagRepository.findAllSortNameAsc()).thenReturn(List.of());
        assertThrows(ServiceNoContentException.class, () -> tagService.findAll(sortType));
    }

    @Test
    void countAll() {
        long countAllTags = 3L;

        when(tagRepository.countAll()).thenReturn(countAllTags);

        long countAllActual = tagService.countAll();
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }
}