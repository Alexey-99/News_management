package com.mjc.school.converter.news;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.model.Author;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.NewsDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConvertToDtoTest {
    @InjectMocks
    private NewsConverter newsConverter;
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private CommentConverter commentConverter;
    @Mock
    private TagConverter tagConverter;

    @Test
    void toDTO_when_newsWithComments() {
        News newsTest = News.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .author(Author.builder().id(2L).build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of(
                        NewsTag.builder().tag(Tag.builder().id(1).build()).build(),
                        NewsTag.builder().tag(Tag.builder().id(2).build()).build(),
                        NewsTag.builder().tag(Tag.builder().id(3).build()).build()))
                .created("created date-time")
                .modified("modified date-time")
                .build();

        when(authorConverter.toDTO(any(Author.class)))
                .thenReturn(AuthorDTO.builder().id(2L).build());

        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(1L)
                .title("news title test")
                .content("news content test")
                .author(AuthorDTO.builder().id(2L).build())
                .countComments(3)
                .countTags(3)
                .created("created date-time")
                .modified("modified date-time")
                .build();

        NewsDTO newsDTOActual = newsConverter.toDTO(newsTest);
        assertEquals(newsDTOExpected, newsDTOActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerNewsWithoutComments")
    void toDTO_when_newsWithoutComments(News news, NewsDTO newsDTO) {
        when(authorConverter.toDTO(any(Author.class)))
                .thenReturn(AuthorDTO.builder().id(2L).build());

        NewsDTO newsDTOActual = newsConverter.toDTO(news);
        assertEquals(newsDTO, newsDTOActual);
    }

    static List<Arguments> providerNewsWithoutComments() {
        return List.of(
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(List.of())
                                .tags(List.of())
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .author(AuthorDTO.builder().id(2L).build())
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()),
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(null)
                                .tags(List.of())
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .author(AuthorDTO.builder().id(2L).build())
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()));
    }

    @ParameterizedTest
    @MethodSource(value = "providerNewsWithoutTags")
    void toDTO_when_newsWithoutTags(News news, NewsDTO newsDTO) {
        when(authorConverter.toDTO(any(Author.class)))
                .thenReturn(AuthorDTO.builder().id(2L).build());

        NewsDTO newsDTOActual = newsConverter.toDTO(news);
        assertEquals(newsDTO, newsDTOActual);
    }

    static List<Arguments> providerNewsWithoutTags() {
        return List.of(
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(List.of())
                                .tags(List.of())
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .author(AuthorDTO.builder().id(2L).build())
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()),
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(null)
                                .tags(null)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .author(AuthorDTO.builder().id(2L).build())
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()));
    }
}