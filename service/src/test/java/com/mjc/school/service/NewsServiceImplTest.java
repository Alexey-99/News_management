package com.mjc.school.service;

import com.mjc.school.converter.impl.NewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

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
    void deleteByAuthorId_when_existsAuthorById_and_foundNewsByAuthorId()
            throws ServiceBadRequestParameterException {
        long authorId = 1;

        when(authorRepository.existsById(authorId)).thenReturn(true);

        when(newsRepository.findByAuthorId(authorId))
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

        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

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
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsDTO newsDTOExpected = NewsDTO.builder().id(newsId).countTags(0).build();
        when(newsConverter.toDTO(newsFromDB)).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.deleteAllTagsFromNews(newsId);
        assertEquals(newsDTOExpected, newsDTOActual);
    }

    @Test
    void update_when_notFoundNewsById() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .build();

        when(newsRepository.findById(newsDTOTesting.getId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_found_news_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_equalNewsTitles_and_notFoundAuthorById() {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).build())
                .build();
        when(newsRepository.findById(newsDTOTesting.getId()))
                .thenReturn(Optional.of(newsFromDB));
        when(authorRepository.findById(newsDTOTesting.getAuthorId()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_exists_author_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_equalNewsTitles_and_foundAuthorById() throws ServiceBadRequestParameterException {
        NewsDTO newsDTOTesting = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .authorId(2)
                .build();

        News newsFromDB = News.builder()
                .id(1)
                .title("news title test")
                .tags(List.of())
                .author(Author.builder().id(1).build())
                .build();
        when(newsRepository.findById(newsDTOTesting.getId()))
                .thenReturn(Optional.of(newsFromDB));

        Author authorFromDB = Author.builder().id(newsDTOTesting.getAuthorId()).build();
        when(authorRepository.findById(newsDTOTesting.getAuthorId()))
                .thenReturn(Optional.of(authorFromDB));

        String currentDateExpected = "date-time";
        when(dateHandler.getCurrentDate()).thenReturn(currentDateExpected);

        newsFromDB.setModified(currentDateExpected);
        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(1)
                .title("news title test")
                .countTags(0)
                .authorId(2)
                .build();
        when(newsConverter.toDTO(newsFromDB)).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.update(newsDTOTesting);
        assertEquals(newsDTOActual, newsDTOExpected);
    }

    @Test
    void update_when_foundNewsById_and_notEqualNewsTitles_and_newsNotExistsByTitle_and_notFoundAuthorById() {
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
        when(newsRepository.findById(newsDTOTesting.getId())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(newsDTOTesting.getTitle())).thenReturn(true);

        when(authorRepository.findById(newsDTOTesting.getAuthorId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("service.exception.not_exists_author_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundNewsById_and_notEqualNewsTitles_and_newsNotExistsByTitle_and_foundAuthorById() throws ServiceBadRequestParameterException {
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
        when(newsRepository.findById(newsDTOTesting.getId())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(newsDTOTesting.getTitle())).thenReturn(true);

        Author authorFromDB = Author.builder().id(newsDTOTesting.getAuthorId()).build();
        when(authorRepository.findById(newsDTOTesting.getAuthorId()))
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
        when(newsConverter.toDTO(newsFromDB)).thenReturn(newsDTOExpected);

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
        when(newsRepository.findById(newsDTOTesting.getId())).thenReturn(Optional.of(newsFromDB));

        when(newsRepository.notExistsByTitle(newsDTOTesting.getTitle())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> newsService.update(newsDTOTesting));
        assertEquals("news_dto.title.not_valid.exists_news_title", exceptionActual.getMessage());
    }

    @Test
    void findAllWithPages_when_notFoundNews() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        when(newsRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(List.of());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> newsService.findAll(page, size, sortField, sortType));

        assertEquals("service.exception.not_found_news", exceptionActual.getMessage());
    }

    @Test
    void findAllWithPages_when_foundNews() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(newsConverter.toDTO(News.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(newsConverter.toDTO(News.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListActual = newsService.findAll(page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllNews_when_notFoundNews() {
        when(newsRepository.findAll()).thenReturn(List.of());
        List<NewsDTO> newsDTOListExpected = List.of();
        List<NewsDTO> newsDTOListActual = newsService.findAll();
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findAllNews_when_foundNews() {
        List<News> newsFindAllList = List.of(
                News.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                News.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                News.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(newsRepository.findAll()).thenReturn(newsFindAllList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(newsConverter.toDTO(News.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(newsConverter.toDTO(News.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<NewsDTO> newsDTOListActual = newsService.findAll();
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void countAllNews() {
        when(newsRepository.countAllNews()).thenReturn(3L);
        long countAllNewsExpected = 3;
        long countAllNewsActual = newsService.countAllNews();
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }

    @Test
    void findById_when_notFoundNewsById() {
        long newsId = 1;

        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> newsService.findById(newsId));
        assertEquals("service.exception.not_found_news_by_id", exceptionActual.getMessage());
    }

    @Test
    void findById_when_foundNewsById() throws ServiceNoContentException {
        long newsId = 1;

        News newsFromDB = News.builder().id(newsId).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsDTO newsDTOExpected = NewsDTO.builder().id(newsId).build();
        when(newsConverter.toDTO(newsFromDB)).thenReturn(newsDTOExpected);

        NewsDTO newsDTOActual = newsService.findById(newsId);
        assertEquals(newsDTOExpected, newsDTOActual);
    }

    @Test
    void findByTagName_when_foundNewsByTagName() throws ServiceNoContentException {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(numberFirstElement);

        Tag tagByTagNameFromDB = Tag.builder().id(2).name(tagName).build();
        List<News> newsFindByTagNameList = List.of(
                News.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .tags(List.of(
                                NewsTag.builder()
                                        .tag(tagByTagNameFromDB)
                                        .build(),
                                NewsTag.builder()
                                        .tag(Tag.builder()
                                                .name("tag_name_2")
                                                .build())
                                        .build()))
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                News.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                News.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .tags(List.of(NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build()))
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsRepository.findByTagName(tagName,
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(newsFindByTagNameList);

        when(newsConverter.toDTO(News.builder().id(1).content("CONTENT 1")
                .tags(List.of(
                        NewsTag.builder()
                                .tag(tagByTagNameFromDB)
                                .build(),
                        NewsTag.builder()
                                .tag(Tag.builder()
                                        .name("tag_name_2")
                                        .build())
                                .build()))
                .modified("2023-10-20T16:05:38.685")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(3)
                .content("CONTENT 3")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
                        .build()))
                .modified("2023-10-20T16:05:32.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build());
        when(newsConverter.toDTO(News.builder()
                .id(2)
                .content("CONTENT 2")
                .tags(List.of(NewsTag.builder()
                        .tag(tagByTagNameFromDB)
                        .build()))
                .modified("2023-10-20T16:05:25.413")
                .build()))
                .thenReturn(NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());

        List<NewsDTO> newsDTOListExpected = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .countTags(2)
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .countTags(1)
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .countTags(1)
                        .build());

        List<NewsDTO> newsDTOListActual = newsService.findByTagName(tagName, page, size, sortField, sortType);
        assertEquals(newsDTOListExpected, newsDTOListActual);
    }

    @Test
    void findByTagName_when_notFoundNewsByTagName() {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(numberFirstElement);
        when(newsRepository.findByTagName(tagName,
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(List.of());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> newsService.findByTagName(tagName, page, size, sortField, sortType));

        assertEquals("service.exception.not_found_news_by_tag_name", exceptionActual.getMessage());
    }

    @Test
    void countAllNewsByTagName() {
        String tagName = "tag_name";

        when(newsRepository.countAllNewsByTagName(tagName)).thenReturn(3L);
        long countAllNewsExpected = 3;
        long countAllNewsActual = newsService.countAllNewsByTagName(tagName);
        assertEquals(countAllNewsExpected, countAllNewsActual);
    }

    @Test
    void findByTagId() {

    }

    @Test
    void countAllNewsByTagId() {
        long tagId = 1;

        when(newsRepository.countAllNewsByTagId(tagId)).thenReturn(2L);
        long countAllNewsExpected = 2;
        long countAllNewsActual = newsService.countAllNewsByTagId(tagId);
        assertEquals(countAllNewsExpected, countAllNewsActual);
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