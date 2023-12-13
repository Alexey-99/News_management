package com.mjc.school.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.AuthorController;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    @InjectMocks
    private AuthorController authorController;
    @Mock
    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            create(): Return status 201.
            If transferred in request correct author.
            """)
    void create_when_everythingOk() throws Exception {
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        when(authorService.create(any(AuthorDTO.class))).thenReturn(true);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(true));

        verify(authorService, times(1)).create(authorDTOTesting);
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of author is not valid. Author name is null.
            """)
    void create_when_notCorrectEnteredAuthorNameByNotNull() throws Exception {
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(null)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.null]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of author is not valid. Author name is blank.
            """)
    void create_when_inCorrectEnteredAuthorNameByNotBlank() throws Exception {
        String incorrectAuthorName = "   ";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(incorrectAuthorName)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.is_blank]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            if size name of tag is not valid.
            """)
    void create_when_inCorrectEnteredTagNameBySize() throws Exception {
        String incorrectAuthorName = "a";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(incorrectAuthorName)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.size]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            deleteById(): Return status 200.
            if correct entered authorId and deleted author by id
            """)
    void deleteById() throws Exception {
        String authorId = "1";

        when(authorService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/author/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        long authorIdL = Long.parseLong(authorId);
        verify(authorService, times(1)).deleteById(authorIdL);
    }

    @Test
    @DisplayName(value = """
            update(): Return status 200 and updated author.
            If correct entered authorId and authorDTO.
            """)
    void update_when_everythingOk() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        AuthorDTO authorDTOUpdated = AuthorDTO.builder()
                .name(authorDTOTesting.getName())
                .build();
        when(authorService.update(any(AuthorDTO.class))).thenReturn(authorDTOUpdated);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(authorDTOUpdated);
                    assertEquals(expectedContentJson, actualContentJson);
                });
        long authorIdL = Long.parseLong(authorId);
        authorDTOTesting.setId(authorIdL);
        verify(authorService, times(1)).update(authorDTOTesting);
    }

    @Test
    @DisplayName(value = """
            update(): throws MethodArgumentNotValidException.
            If correct entered authorId and entered name of author is not valid. Author name is null.
            """)
    void update_when_correctAuthorId_and_authorNameNull() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(null)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.null]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            update(): throws MethodArgumentNotValidException.
            If correct entered authorId and entered name of author is not valid. Author name is blank.
            """)
    void update_when_correctTagId_and_tagNameBlank() throws Exception {
        String authorId = "1";

        String authorNameIncorrect = "   ";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(authorNameIncorrect)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.is_blank]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            update(): throws MethodArgumentNotValidException.
            If correct entered authorId and entered size name of author is not valid.
            """)
    void update_when_correctTagId_and_tagNameSize() throws Exception {
        String authorId = "1";

        String authorNameIncorrect = "a";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(authorNameIncorrect)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [author_dto.name.not_valid.size]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of authors.
               """)
    void findAll() throws Exception {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        int maxNumberPageExpected = 2;
        long countAllElementsExpected = 10;

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").countNews(4).build(),
                AuthorDTO.builder().id(3).name("Bam").countNews(2).build(),
                AuthorDTO.builder().id(2).name("Sem").countNews(6).build(),
                AuthorDTO.builder().id(4).name("Tom").countNews(0).build(),
                AuthorDTO.builder().id(5).name("Van").countNews(0).build());

        when(authorService.findAll(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(authorDTOListExpected);
        when(authorService.countAll()).thenReturn(countAllElementsExpected);

        Pagination<AuthorDTO> authorDTOPaginationExpected = Pagination.<AuthorDTO>builder()
                .entity(authorDTOListExpected)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(authorService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(authorDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/author/all")
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
                    String expectedContentJson = objectMapper.writeValueAsString(authorDTOPaginationExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findById(): Return status 200 and found author by entered id.
            """)
    void findById() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOExpected = AuthorDTO.builder()
                .id(Long.parseLong(authorId))
                .name("Author_name")
                .build();
        when(authorService.findById(anyLong())).thenReturn(authorDTOExpected);

        mockMvc.perform(get("/api/v2/author/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(authorDTOExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findByPartOfName(): Return status 200 and found list of authors by entered part of name.
            """)
    void findByPartOfName() throws Exception {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "partOfName";
        int maxNumberPageExpected = 2;

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex_partOfName").countNews(4).build(),
                AuthorDTO.builder().id(3).name("B_partOfName_Bam").countNews(2).build(),
                AuthorDTO.builder().id(2).name("Se_partOfName_m").countNews(6).build(),
                AuthorDTO.builder().id(4).name("T_partOfName_om").countNews(0).build(),
                AuthorDTO.builder().id(5).name("Van_partOfName").countNews(0).build());
        when(authorService.findByPartOfName(anyString(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(authorDTOListExpected);

        long countAllElementsExpected = 10;
        when(authorService.countAllByPartOfName(partOfName)).thenReturn(countAllElementsExpected);

        Pagination<AuthorDTO> authorDTOPaginationExpected = Pagination.<AuthorDTO>builder()
                .entity(authorDTOListExpected)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(authorService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(authorDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/author/part-name/{partOfName}", partOfName)
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
                    String expectedContentJson = objectMapper.writeValueAsString(authorDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            findByNewsId(): Return status 200 and found list of authors by entered newsId.
            """)
    void findByNewsId() throws Exception {
        String newsId = "1";

        AuthorDTO authorDTOExpected = AuthorDTO.builder()
                .id(1)
                .name("Alex_partOfName")
                .countNews(4)
                .build();
        when(authorService.findByNewsId(anyLong())).thenReturn(authorDTOExpected);

        mockMvc.perform(get("/api/v2/author/news/{newsId}", newsId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(authorDTOExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    @DisplayName(value = """
            selectAllAuthorsIdWithAmountOfWrittenNews(): Return status 200 and found list of authors with written news.
            """)
    void selectAllAuthorsIdWithAmountOfWrittenNews() throws Exception {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        long countAllElementsExpected = 10;
        int maxNumberPageExpected = 2;

        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListExpected = List.of(
                new AuthorIdWithAmountOfWrittenNewsDTO(2, 6),
                new AuthorIdWithAmountOfWrittenNewsDTO(1, 4),
                new AuthorIdWithAmountOfWrittenNewsDTO(3, 2),
                new AuthorIdWithAmountOfWrittenNewsDTO(4, 0),
                new AuthorIdWithAmountOfWrittenNewsDTO(5, 0));

        when(authorService.findAllAuthorsIdWithAmountOfWrittenNews(anyInt(), anyInt(), anyString()))
                .thenReturn(authorIdWithAmountOfWrittenNewsDTOListExpected);

        when(authorService.countAll()).thenReturn(countAllElementsExpected);

        Pagination<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOPaginationExpected =
                Pagination.<AuthorIdWithAmountOfWrittenNewsDTO>builder()
                        .entity(authorIdWithAmountOfWrittenNewsDTOListExpected)
                        .size(size)
                        .numberPage(page)
                        .maxNumberPage(maxNumberPageExpected)
                        .build();
        when(authorService.getPaginationAuthorIdWithAmountOfWrittenNews(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(authorIdWithAmountOfWrittenNewsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/author/amount-news")
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(authorIdWithAmountOfWrittenNewsDTOPaginationExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }
}