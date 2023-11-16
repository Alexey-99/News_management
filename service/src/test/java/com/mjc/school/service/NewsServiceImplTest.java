package com.mjc.school.service;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.service.news.impl.sort.NewsSortField;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {
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
    @Mock
    private PaginationService paginationService;

    @Test
    void create_when_notExistsNewsByTitle() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder().id(1).title("News_title_test").build();

        when(newsRepository.notExistsByTitle(newsDTOTesting.getTitle())).thenReturn(true);

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

    @Test
    void deleteById_when_newsExistsById() {
        long newsId = 1;

        when(newsRepository.existsById(newsId)).thenReturn(true);

        boolean actualResult = newsService.deleteById(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_newsNotExistsById() {
        long newsId = 1;

        when(newsRepository.existsById(newsId)).thenReturn(false);

        boolean actualResult = newsService.deleteById(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByAuthorId_when_existsAuthorById() throws ServiceBadRequestParameterException {
        long authorId = 1;

        when(authorRepository.existsById(authorId)).thenReturn(true);

        boolean actualResult = newsService.deleteByAuthorId(authorId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByAuthorId_when_notExistsAuthorById() {
        long authorId = 1;

        when(authorRepository.existsById(authorId)).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.deleteByAuthorId(authorId));
        assertEquals("service.exception.not_found_author_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void deleteAllTagsFromNews() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void countAllNews() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByTagName() {
    }

    @Test
    void countAllNewsByTagName() {
    }

    @Test
    void findByTagId() {
    }

    @Test
    void countAllNewsByTagId() {
    }

    @Test
    void findByPartOfAuthorName() {
    }

    @Test
    void countAllNewsByPartOfAuthorName() {
    }

    @Test
    void findByAuthorId() {
    }

    @Test
    void countAllNewsByAuthorId() {
    }

    @Test
    void findByPartOfTitle() {
    }

    @Test
    void countAllNewsByPartOfTitle() {
    }

    @Test
    void findByPartOfContent() {
    }

    @Test
    void countAllNewsByPartOfContent() {
    }

    @Test
    void getPagination() {
        List<NewsDTO> newsDTOList = List.of(
                NewsDTO.builder().id(2).build(),
                NewsDTO.builder().id(1).build(),
                NewsDTO.builder().id(3).build(),
                NewsDTO.builder().id(4).build(),
                NewsDTO.builder().id(5).build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(countAllElements, size)).thenReturn(2);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsDTOList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(2)
                .build();
        Pagination<NewsDTO> newsDTOPaginationActual =
                newsService.getPagination(newsDTOList, countAllElements, page, size);
        assertEquals(newsDTOPaginationExpected, newsDTOPaginationActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldParams_when_foundSortField")
    void getOptionalSortField_when_foundSortField(String sortField, String sortFieldExpected) {
        String sortFieldActual = newsService.getOptionalSortField(sortField)
                .get()
                .name()
                .toLowerCase();
        assertEquals(sortFieldExpected, sortFieldActual);
    }

    static List<Arguments> providerSortFieldParams_when_foundSortField() {
        return List.of(
                Arguments.of("created", NewsSortField.CREATED.name().toLowerCase()),
                Arguments.of("modified", NewsSortField.MODIFIED.name().toLowerCase())
        );
    }

    @Test
    void getOptionalSortField_when_sortFieldIsNull() {
        Optional<NewsSortField> optionalActual = newsService.getOptionalSortField(null);
        assertTrue(optionalActual.isEmpty());
    }

    @Test
    void getOptionalSortField_when_notFoundSortField() {
        Optional<NewsSortField> optionalActual =
                newsService.getOptionalSortField("not_found_sort_field");
        assertTrue(optionalActual.isEmpty());
    }
}