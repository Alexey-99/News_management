package com.mjc.school.converter;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.Author;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.NewsDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsConverterTest {
    @InjectMocks
    private NewsConverter newsConverter;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsTagRepository newsTagRepository;
    private static News newsActual;
    private static News newsExpected;
    private static NewsDTO newsDTOTesting;

    @Test()
    void fromDTO_when_notFoundAuthor_throwException() {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> newsConverter.fromDTO(newsDTOTesting)
        );
        assertEquals("service.exception.not_exists_author_by_id", exception.getMessage());
    }

    @Test
    void fromDTO_when_foundAuthor() throws ServiceBadRequestParameterException {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L))
                .thenReturn(Optional.of(Author.builder().id(2L).build()));
        newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).build())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .tags(List.of())
                .comments(List.of())
                .build();
        newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_foundComments() throws ServiceBadRequestParameterException {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L))
                .thenReturn(Optional.of(Author.builder().id(2L).build()));
        when(commentRepository.findByNewsId(1L))
                .thenReturn(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()));
        newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_notFoundComments() throws ServiceBadRequestParameterException {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L))
                .thenReturn(Optional.of(Author.builder().id(2L).build()));
        when(commentRepository.findByNewsId(1L)).thenReturn(List.of());
        newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).build())
                .comments(List.of())
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_foundTags() throws ServiceBadRequestParameterException {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L)).thenReturn(Optional.of(Author.builder().id(2L).build()));
        when(commentRepository.findByNewsId(1L)).thenReturn(List.of(
                Comment.builder().id(1).build(),
                Comment.builder().id(2).build(),
                Comment.builder().id(3).build()));
        when(newsTagRepository.findByNewsId(1L)).thenReturn(List.of(
                NewsTag.builder()
                        .id(1)
                        .news(News.builder().id(1L).build())
                        .tag(Tag.builder().id(1).build())
                        .build(),
                NewsTag.builder()
                        .id(2)
                        .news(News.builder().id(1L).build())
                        .tag(Tag.builder().id(2).build())
                        .build(),
                NewsTag.builder()
                        .id(3)
                        .news(News.builder().id(1L).build())
                        .tag(Tag.builder().id(3).build())
                        .build()));
        newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of(
                        NewsTag.builder()
                                .id(1)
                                .news(News.builder().id(1L).build())
                                .tag(Tag.builder().id(1).build())
                                .build(),
                        NewsTag.builder()
                                .id(2)
                                .news(News.builder().id(1L).build())
                                .tag(Tag.builder().id(2).build())
                                .build(),
                        NewsTag.builder()
                                .id(3)
                                .news(News.builder().id(1L).build())
                                .tag(Tag.builder().id(3).build())
                                .build()))
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_notFoundTags() throws ServiceBadRequestParameterException {
        newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findById(2L)).thenReturn(Optional.of(Author.builder().id(2L).build()));
        when(commentRepository.findByNewsId(1L)).thenReturn(List.of(
                Comment.builder().id(1).build(),
                Comment.builder().id(2).build(),
                Comment.builder().id(3).build()));
        when(newsTagRepository.findByNewsId(1L)).thenReturn(List.of());
        newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }


    @ParameterizedTest
    @MethodSource(value = "providerNewsParams")
    void toDTO(News news, NewsDTO newsDTOExpected) {
        NewsDTO newsDTOActual = newsConverter.toDTO(news);
        assertEquals(newsDTOExpected, newsDTOActual);
    }

    static List<Arguments> providerNewsParams() {
        return List.of(
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(List.of(
                                        Comment.builder().id(1).build(),
                                        Comment.builder().id(2).build(),
                                        Comment.builder().id(3).build()))
                                .tags(List.of())
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .authorId(2L)
                                .countComments(3)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                ),
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
                                .authorId(2L)
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                ),
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
                                .authorId(2L)
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                ),

                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(List.of())
                                .tags(List.of(
                                        NewsTag.builder()
                                                .id(1L)
                                                .news(News.builder().id(1L).build())
                                                .tag(Tag.builder().id(1L).build())
                                                .build(),
                                        NewsTag.builder()
                                                .id(2L)
                                                .news(News.builder().id(1L).build())
                                                .tag(Tag.builder().id(2L).build())
                                                .build(),
                                        NewsTag.builder()
                                                .id(3L)
                                                .news(News.builder().id(1L).build())
                                                .tag(Tag.builder().id(3L).build())
                                                .build(),
                                        NewsTag.builder()
                                                .id(4L)
                                                .news(News.builder().id(1L).build())
                                                .tag(Tag.builder().id(4L).build())
                                                .build()))
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .authorId(2L)
                                .countComments(0)
                                .countTags(4)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                ),
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
                                .authorId(2L)
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                ),
                Arguments.of(
                        News.builder()
                                .id(1)
                                .title("news title test")
                                .content("news content test")
                                .author(Author.builder().id(2L).build())
                                .comments(List.of())
                                .tags(null)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build(),
                        NewsDTO.builder()
                                .id(1L)
                                .title("news title test")
                                .content("news content test")
                                .authorId(2L)
                                .countComments(0)
                                .countTags(0)
                                .created("created date-time")
                                .modified("modified date-time")
                                .build()
                )
        );
    }

    @AfterAll
    static void afterAll() {
        newsDTOTesting = null;
        newsActual = null;
        newsExpected = null;
    }
}