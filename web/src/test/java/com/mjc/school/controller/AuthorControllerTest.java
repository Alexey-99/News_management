package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.TagDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByPartOfName() {
    }

    @Test
    void findByNewsId() {
    }

    @Test
    void selectAllAuthorsIdWithAmountOfWrittenNews() {
    }
}