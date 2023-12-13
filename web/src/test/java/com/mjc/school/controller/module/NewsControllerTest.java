package com.mjc.school.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.NewsController;
import com.mjc.school.service.news.NewsService;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NewsControllerTest {
    @InjectMocks
    private NewsController newsController;
    @Mock
    private NewsService newsService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            create(): Return status 201.
            If transferred in request correct news.
            """)
    void create() {
    }

    @Test
    @DisplayName(value = """
            deleteById(): Return status 200.
            if correct entered newsId and deleted news by id
            """)
    void deleteById() throws Exception {
        String newsId = "1";

        when(newsService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/news/{id}", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        long newsIdL = Long.parseLong(newsId);
        verify(newsService, times(1)).deleteById(newsIdL);
    }

    @Test
    @DisplayName(value = """
            deleteByAuthorId(): Return status 200.
            if correct entered authorId and deleted news by authorId
            """)
    void deleteByAuthorId() throws Exception {
        String authorId = "1";

        when(newsService.deleteByAuthorId(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/news/author/{authorId}", authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        long authorIdL = Long.parseLong(authorId);
        verify(newsService, times(1)).deleteByAuthorId(authorIdL);
    }

    @Test
    @DisplayName(value = """
            deleteAllTagsFromNewsByNewsId(): Return status 200.
            if correct entered newsId and deleted all tags from news by newsId
            """)
    void deleteAllTagsFromNewsByNewsId() throws Exception {
        String newsId = "1";

        NewsDTO newsDTOExpected = NewsDTO.builder().build();
        when(newsService.deleteAllTagsFromNews(anyLong())).thenReturn(newsDTOExpected);

        mockMvc.perform(delete("/api/v2/news/all-tags/{newsId}", newsId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });

        long newsIdL = Long.parseLong(newsId);
        verify(newsService, times(1)).deleteAllTagsFromNews(newsIdL);
    }

    @Test
    @DisplayName(value = """
            update(): Return status 200 and updated news.
            If correct entered newsId and newsDTO.
            """)
    void update() {
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and Pagination of news.
               """)
    void findAll() throws Exception {
        int maxNumberPageExpected = 2;
        long countAllElementsExpected = 10;
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        List<NewsDTO> newsFindAllList = List.of(
                NewsDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                NewsDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                NewsDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build(),
                NewsDTO.builder().id(4).content("CONTENT 4")
                        .modified("2023-10-20T16:05:20.413").build(),
                NewsDTO.builder().id(5).content("CONTENT 5")
                        .modified("2023-10-20T16:05:15.413").build());

        when(newsService.findAll(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsFindAllList);

        when(newsService.countAllNews()).thenReturn(countAllElementsExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsFindAllList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/all")
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findById(): Return status 200 and found news by id.
               """)
    void findById() throws Exception {
        String newsId = "1";

        long newsIdL = Long.parseLong(newsId);
        NewsDTO newsDTOExpected = NewsDTO.builder()
                .id(newsIdL)
                .content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685")
                .build();
        when(newsService.findById(anyLong())).thenReturn(newsDTOExpected);

        mockMvc.perform(get("/api/v2/news/{id}", newsId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByTagName(): Return status 200 and pagination of news by tag name.
               """)
    void findNewsByTagName() throws Exception {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";
        int maxNumberPageExpected = 1;

        List<NewsDTO> newsFindByTagNameList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByTagName(anyString(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsFindByTagNameList);

        long countAllNewsByTagNameExpected = 3;
        when(newsService.countAllNewsByTagName(anyString()))
                .thenReturn(countAllNewsByTagNameExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsFindByTagNameList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/tag-name/{tagName}", tagName)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByTagId(): Return status 200 and pagination of news by tag id.
               """)
    void findNewsByTagId() throws Exception {
        String tagId = "1";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";
        int maxNumberPageExpected = 1;

        List<NewsDTO> newsFindByTagNameList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .countTags(2)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .countTags(1)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .countTags(1)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByTagId(anyLong(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsFindByTagNameList);

        long countAllNewsByTagNameExpected = 3;
        when(newsService.countAllNewsByTagId(anyLong())).thenReturn(countAllNewsByTagNameExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsFindByTagNameList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/tag/{tagId}", tagId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByAuthorName(): Return status 200 and pagination of news by author name.
               """)
    void findNewsByAuthorName() throws Exception {
        String partOfAuthorName = "partOfAuthorName";

        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 1;
        String sortType = "DESC";
        String sortField = "modified";

        List<NewsDTO> newsByAuthorNameList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .authorId(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .authorId(3)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .authorId(2)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByPartOfAuthorName(anyString(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsByAuthorNameList);

        long countAllNewsByPartOfAuthorNameExpected = 3;
        when(newsService.countAllNewsByPartOfAuthorName(anyString()))
                .thenReturn(countAllNewsByPartOfAuthorNameExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsByAuthorNameList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/author/part-name/{partOfAuthorName}", partOfAuthorName)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByAuthorId(): Return status 200 and pagination of news by author id.
               """)
    void findNewsByAuthorId() throws Exception {
        String authorId = "1";

        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 1;
        String sortType = "DESC";
        String sortField = "modified";

        List<NewsDTO> newsByAuthorIdList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT 1")
                        .authorId(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("CONTENT 3")
                        .authorId(3)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2")
                        .authorId(2)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByAuthorId(anyLong(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsByAuthorIdList);

        long countAllNewsByAuthorIdExpected = 3;
        when(newsService.countAllNewsByAuthorId(anyLong()))
                .thenReturn(countAllNewsByAuthorIdExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsByAuthorIdList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/author/{authorId}", authorId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByPartOfTitle(): Return status 200 and pagination of news by part of title.
               """)
    void findNewsByPartOfTitle() throws Exception {
        String partOfTitle = "partOfTitle";

        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 1;
        String sortType = "DESC";
        String sortField = "modified";

        List<NewsDTO> newsByPartOfTitleList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT partOfTitle 1")
                        .authorId(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("partOfTitle CONTENT 3")
                        .authorId(3)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2 partOfTitle")
                        .authorId(2)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByPartOfTitle(anyString(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsByPartOfTitleList);

        long countAllNewsByPartOfTitleExpected = 3;
        when(newsService.countAllNewsByPartOfTitle(anyString()))
                .thenReturn(countAllNewsByPartOfTitleExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsByPartOfTitleList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/part-title/{partOfTitle}", partOfTitle)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findNewsByPartOfContent(): Return status 200 and pagination of news by part of content.
               """)
    void findNewsByPartOfContent() throws Exception {
        String partOfContent = "partOfContent";

        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 1;
        String sortType = "DESC";
        String sortField = "modified";

        List<NewsDTO> newsByPartOfContentList = List.of(
                NewsDTO.builder()
                        .id(1)
                        .content("CONTENT partOfTitle 1")
                        .authorId(1)
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                NewsDTO.builder()
                        .id(3)
                        .content("partOfTitle CONTENT 3")
                        .authorId(3)
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                NewsDTO.builder()
                        .id(2)
                        .content("CONTENT 2 partOfTitle")
                        .authorId(2)
                        .modified("2023-10-20T16:05:25.413")
                        .build());
        when(newsService.findByPartOfContent(anyString(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(newsByPartOfContentList);

        long countAllNewsByPartOfContentExpected = 3;
        when(newsService.countAllNewsByPartOfContent(anyString()))
                .thenReturn(countAllNewsByPartOfContentExpected);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsByPartOfContentList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(newsService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/news/part-content/{partOfContent}", partOfContent)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }
}