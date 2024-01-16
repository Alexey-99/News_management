package com.mjc.school.converter;

import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TagConverterTest {
    @InjectMocks
    private TagConverter tagConverter;

    @Test
    void fromDTO() {
        TagDTO tagDTO = TagDTO.builder()
                .id(1)
                .name("Tag_name_test")
                .build();
        Tag tagExpected = Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
        Tag tagActual = tagConverter.fromDTO(tagDTO);
        assertEquals(tagExpected, tagActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerTagParams")
    void toDTO(Tag tag, TagDTO tagDTOExpected) {
        TagDTO tagDTOActual = tagConverter.toDTO(tag);
        assertEquals(tagDTOExpected, tagDTOActual);
    }

    static List<Arguments> providerTagParams() {
        return List.of(
                Arguments.of(
                        Tag.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .news(List.of())
                                .build(),
                        TagDTO.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .countNews(0)
                                .build()),
                Arguments.of(
                        Tag.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .news(List.of(
                                        NewsTag.builder()
                                                .tag(Tag.builder()
                                                        .id(1)
                                                        .name("Tag_name_test")
                                                        .build())
                                                .news(News.builder()
                                                        .id(1)
                                                        .build())
                                                .build(),
                                        NewsTag.builder()
                                                .tag(Tag.builder()
                                                        .id(1)
                                                        .name("Tag_name_test")
                                                        .build())
                                                .news(News.builder()
                                                        .id(2)
                                                        .build())
                                                .build(),
                                        NewsTag.builder()
                                                .tag(Tag.builder()
                                                        .id(1)
                                                        .name("Tag_name_test")
                                                        .build())
                                                .news(News.builder()
                                                        .id(3)
                                                        .build())
                                                .build()))
                                .build(),
                        TagDTO.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .countNews(3)
                                .build()),
                Arguments.of(
                        Tag.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .news(null)
                                .build(),
                        TagDTO.builder()
                                .id(1)
                                .name("Tag_name_test")
                                .countNews(0)
                                .build())
        );
    }
}