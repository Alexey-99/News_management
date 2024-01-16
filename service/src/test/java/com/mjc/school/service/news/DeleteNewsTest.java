package com.mjc.school.service.news;

import com.mjc.school.converter.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private NewsConverter newsConverter;

    @Test
    void deleteById_when_newsExistsById() {
        long newsId = 1;

        when(newsRepository.existsById(anyLong())).thenReturn(true);

        boolean actualResult = newsService.deleteById(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_newsNotExistsById() {
        long newsId = 1;

        when(newsRepository.existsById(anyLong())).thenReturn(false);

        boolean actualResult = newsService.deleteById(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByAuthorId_when_existsAuthorById() throws ServiceBadRequestParameterException {
        long authorId = 1;

        when(authorRepository.existsById(anyLong())).thenReturn(true);

        boolean actualResult = newsService.deleteByAuthorId(authorId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByAuthorId_when_notExistsAuthorById() {
        long authorId = 1;

        when(authorRepository.existsById(anyLong())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.deleteByAuthorId(authorId));
        assertEquals("service.exception.not_found_author_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void deleteByAuthorId_when_existsAuthorById_and_foundNewsByAuthorId()
            throws ServiceBadRequestParameterException {
        long authorId = 1;

        when(authorRepository.existsById(anyLong())).thenReturn(true);

        when(newsRepository.findByAuthorId(anyLong()))
                .thenReturn(List.of(
                        News.builder().id(1).author(Author.builder().id(3).build()).build(),
                        News.builder().id(2).author(Author.builder().id(2).build()).build(),
                        News.builder().id(3).author(Author.builder().id(1).build()).build()));
        boolean actualResult = newsService.deleteByAuthorId(authorId);
        assertTrue(actualResult);
    }

    @Test
    void deleteAllTagsFromNews_when_notFoundNewsById() {
        long newsId = 1;

        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.deleteAllTagsFromNews(newsId));
        assertEquals("service.exception.not_found_news_by_id", exceptionActual.getMessage());
    }

    @Test
    void deleteAllTagsFromNews_when_foundNewsById() throws ServiceBadRequestParameterException {
        long newsId = 1;

        News newsFromDB = News.builder().id(newsId).build();
        List<NewsTag> newsTagList = List.of(
                NewsTag.builder()
                        .id(1)
                        .news(newsFromDB)
                        .tag(Tag.builder().id(1).build())
                        .build(),
                NewsTag.builder()
                        .id(2)
                        .news(newsFromDB)
                        .tag(Tag.builder().id(2).build())
                        .build(),
                NewsTag.builder()
                        .id(3)
                        .news(newsFromDB)
                        .tag(Tag.builder().id(3).build())
                        .build());
        newsFromDB.setTags(newsTagList);
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(newsFromDB));

        NewsDTO newsDTOExpected = NewsDTO.builder().id(newsId).countTags(0).build();
        when(newsConverter.toDTO(any(News.class))).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.deleteAllTagsFromNews(newsId);
        assertEquals(newsDTOExpected, newsDTOActual);
    }
}
