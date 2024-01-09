package com.mjc.school.converter;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.CommentConverter;
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
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private CommentConverter commentConverter;

    @Test
    void fromDTO_when_notFoundAuthor_throwException() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> newsConverter.fromDTO(newsDTOTesting)
        );
        assertEquals("service.exception.not_exists_author_by_name", exception.getMessage());
    }

    @Test
    void fromDTO_when_foundAuthor() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(Author.builder().id(2L).name("Tom").build()));
        News newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).name("Tom").build())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .tags(List.of())
                .comments(List.of())
                .build();
        News newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_foundComments() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(Author.builder().id(2L).name("Tom").build()));
        when(commentRepository.findByNewsId(1L))
                .thenReturn(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()));
        News newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).name("Tom").build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        News newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_notFoundComments() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(Author.builder().id(2L).name("Tom").build()));
        when(commentRepository.findByNewsId(anyLong())).thenReturn(List.of());
        News newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).name("Tom").build())
                .comments(List.of())
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        News newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_foundTags() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(Author.builder().id(2L).name("Tom").build()));
        when(commentRepository.findByNewsId(anyLong())).thenReturn(List.of(
                Comment.builder().id(1).build(),
                Comment.builder().id(2).build(),
                Comment.builder().id(3).build()));
        when(newsTagRepository.findByNewsId(anyLong()))
                .thenReturn(List.of(
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
        News newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).name("Tom").build())
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
        News newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void fromDTO_when_notFoundTags() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .content("news content test")
                .authorName("Tom")
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(Author.builder().id(2L).name("Tom").build()));
        when(commentRepository.findByNewsId(anyLong()))
                .thenReturn(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()));
        when(newsTagRepository.findByNewsId(1L)).thenReturn(List.of());
        News newsExpected = News.builder()
                .id(newsDTOTesting.getId())
                .title(newsDTOTesting.getTitle())
                .content(newsDTOTesting.getContent())
                .author(Author.builder().id(2L).name("Tom").build())
                .comments(List.of(
                        Comment.builder().id(1).build(),
                        Comment.builder().id(2).build(),
                        Comment.builder().id(3).build()))
                .tags(List.of())
                .created(newsDTOTesting.getCreated())
                .modified(newsDTOTesting.getModified())
                .build();
        News newsActual = newsConverter.fromDTO(newsDTOTesting);
        assertEquals(newsExpected, newsActual);
    }

    @Test
    void toDTO() {
        when(authorConverter.toDTO(any(Author.class)))
                .thenReturn(AuthorDTO.builder().id(2L).build());
        when(commentConverter.toDTO(Comment.builder().id(1).build()))
                .thenReturn(CommentDTO.builder().id(1).build());
        when(commentConverter.toDTO(Comment.builder().id(2).build()))
                .thenReturn(CommentDTO.builder().id(2).build());
        when(commentConverter.toDTO(Comment.builder().id(3).build()))
                .thenReturn(CommentDTO.builder().id(3).build());

        News newsTest = News.builder()
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
                .build();

        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(1L)
                .title("news title test")
                .content("news content test")
                .author(AuthorDTO.builder().id(2L).build())
                .comments(List.of(
                        CommentDTO.builder().id(1).build(),
                        CommentDTO.builder().id(2).build(),
                        CommentDTO.builder().id(3).build()))
                .tags(List.of())
                .created("created date-time")
                .modified("modified date-time")
                .build();

        NewsDTO newsDTOActual = newsConverter.toDTO(newsTest);
        assertEquals(newsDTOExpected, newsDTOActual);
    }
}