package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.validation.dto.AuthorDTO;
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
class UpdateNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private NewsConverter newsConverter;
    @Mock
    private DateHandler dateHandler;

    @Test
    void update_when_notFoundNewsById() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .build();

        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_found_news_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_equalNewsTitles_and_notFoundAuthorByName() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .authorName("Max")
                .countTags(0)
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).build())
                .build();
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(newsFromDB));
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_exists_author_by_name", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_equalNewsTitles_and_foundAuthorByName() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .authorName("Max")
                .countTags(0)
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).build())
                .build();
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(newsFromDB));

        Author authorFromDB = Author.builder().id(newsDTOTesting.getAuthorId()).build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(authorFromDB));

        String currentDateExpected = "date-time";
        when(dateHandler.getCurrentDate()).thenReturn(currentDateExpected);

        newsFromDB.setModified(currentDateExpected);
        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .author(AuthorDTO.builder().id(2).name("Max").build())
                .authorId(2)
                .build();
        when(newsConverter.toDTO(any(News.class))).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.update(newsDTOTesting);
        assertEquals(newsDTOActual, newsDTOExpected);
    }

    @Test
    void update_when_foundNewsById_and_notEqualNewsTitles_and_newsNotExistsByTitle_and_notFoundAuthorByName() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test other")
                .countTags(0)
                .authorName("Max")
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).name("Max").build())
                .build();
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(anyString())).thenReturn(true);

        when(authorRepository.findByName(anyString())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_exists_author_by_name", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_notEqualNewsTitles_and_newsNotExistsByTitle_and_foundAuthorByName() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test other")
                .countTags(0)
                .authorName("Max")
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).name("Max").build())
                .build();
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(anyString())).thenReturn(true);

        Author authorFromDB = Author.builder()
                .id(newsDTOTesting.getAuthorId())
                .name(newsDTOTesting.getAuthorName())
                .build();
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(authorFromDB));

        String currentDateExpected = "date-time";
        when(dateHandler.getCurrentDate()).thenReturn(currentDateExpected);

        newsFromDB.setModified(currentDateExpected);
        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(1)
                .title("news title test other")
                .countTags(0)
                .authorId(2)
                .build();
        when(newsConverter.toDTO(any(News.class))).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.update(newsDTOTesting);
        assertEquals(newsDTOActual, newsDTOExpected);
    }

    @Test
    void update_when_foundNewsById_and_notEqualNewsTitles_and_newsExistsByTitle() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test other")
                .countTags(0)
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).build())
                .build();
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(anyString())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("news_dto.title.not_valid.exists_news_title", exceptionActual.getMessage());
    }
}
