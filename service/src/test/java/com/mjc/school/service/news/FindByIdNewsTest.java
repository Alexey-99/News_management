package com.mjc.school.service.news;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.validation.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;

    @Test
    void findById_when_notFoundNewsById() {
        long newsId = 1;

        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ServiceNoContentException.class,
                () -> newsService.findById(newsId));
    }

    @Test
    void findById_when_foundNewsById() throws ServiceNoContentException {
        long newsId = 1;

        News newsFromDB = News.builder().id(newsId).build();
        when(newsRepository.findById(anyLong()))
                .thenReturn(Optional.of(newsFromDB));

        NewsDTO newsDTOExpected = NewsDTO.builder().id(newsId).build();
        when(newsConverter.toDTO(any(News.class)))
                .thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.findById(newsId);
        assertEquals(newsDTOExpected, newsDTOActual);
    }
}