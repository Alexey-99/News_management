package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.impl.TagServiceImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddToNewsTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsTagRepository newsTagRepository;

    @Test
    void addToNews_when_notFoundTagById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.not_found_tag_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_notFoundNewsById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId))
                .thenReturn(Optional.of(Tag.builder().id(tagId).build()));
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.not_found_news_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_foundNewsById_and_tagPresentInNews() {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsTag newsTag = NewsTag.builder().id(1).tag(tagFromDB).news(newsFromDB).build();
        tagFromDB.setNews(List.of(newsTag));
        newsFromDB.setTags(List.of(newsTag));

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.exists_tag_in_news",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_foundNewsById_and_tagNotPresentInNews() throws ServiceBadRequestParameterException {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).news(List.of()).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).tags(List.of()).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        when(newsTagRepository.save(any(NewsTag.class))).thenReturn(null);

        boolean actualResult = tagService.addToNews(tagId, newsId);
        assertTrue(actualResult);
    }
}