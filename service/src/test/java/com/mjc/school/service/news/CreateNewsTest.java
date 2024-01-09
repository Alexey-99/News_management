package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.validation.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private DateHandler dateHandler;
    @Mock
    private NewsConverter newsConverter;

    @Test
    void create_when_notExistsNewsByTitle() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder().id(1).title("News_title_test").build();

        when(newsRepository.notExistsByTitle(anyString())).thenReturn(true);

        boolean actualResult = newsService.create(newsDTOTesting);
        assertTrue(actualResult);
    }

    @Test
    void create_when_existsNewsByTitle() {
        NewsDTO newsDTOTesting = NewsDTO.builder().id(1).title("News_title_test").build();

        when(newsRepository.notExistsByTitle(newsDTOTesting.getTitle())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.create(newsDTOTesting));

        assertEquals("news_dto.title.not_valid.exists_news_title",
                exceptionActual.getMessage());
    }
}
