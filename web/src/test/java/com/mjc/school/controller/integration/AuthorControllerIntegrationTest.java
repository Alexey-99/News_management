package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class AuthorControllerIntegrationTest {
    private static final String AUTHORIZATION_HEADER_VALUE_START_WITH = "Bearer ";
    private String adminJwtToken = null;
    private String userJwtToken = null;
    @Autowired
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws ServiceBadRequestParameterException {
        if (adminJwtToken == null) {
            CreateJwtTokenRequest createJwtTokenRequestAdmin = new CreateJwtTokenRequest("user", "123456");
            CreateJwtTokenRequest createJwtTokenRequestUser = new CreateJwtTokenRequest("user_2", "123456");
            adminJwtToken = authService.createAuthToken(createJwtTokenRequestAdmin);
            userJwtToken = authService.createAuthToken(createJwtTokenRequestUser);
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of authors.
               """)
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v2/author/all"))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void createAuthor_admin() throws Exception {
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .id(3)
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(authorDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void createAuthor_user() throws Exception {
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .id(3)
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(authorDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of author is not valid. Author name is null.
            """)
    void create_admin_when_notCorrectEnteredAuthorNameByNotNull() throws Exception {
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(null)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of author is not valid. Author name is blank.
            """)
    void create_admin_when_inCorrectEnteredAuthorNameByNotBlank() throws Exception {
        String incorrectAuthorName = "   ";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(incorrectAuthorName)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            if size name of tag is not valid.
            """)
    void create_admin_when_inCorrectEnteredTagNameBySize() throws Exception {
        String incorrectAuthorName = "a";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(incorrectAuthorName)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(post("/api/v2/author")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_guest() throws Exception {
        String authorId = "1";
        mockMvc.perform(delete("/api/v2/author/{id}", authorId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteById_user() throws Exception {
        String authorId = "1";
        mockMvc.perform(delete("/api/v2/author/{id}", authorId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById_admin() throws Exception {
        String authorId = "1";

        mockMvc.perform(delete("/api/v2/author/{id}", authorId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void update_user() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void update_admin() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void update_guest() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);
        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName(value = """
            update(): 400 status.
            If correct entered authorId and entered name of author is not valid. Author name is null.
            """)
    void update_admin_when_correctAuthorId_and_authorNameNull() throws Exception {
        String authorId = "1";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(null)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            update(): 400 status.
            If correct entered authorId and entered name of author is not valid. Author name is blank.
            """)
    void update_admin_when_correctTagId_and_tagNameBlank() throws Exception {
        String authorId = "1";

        String authorNameIncorrect = "   ";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(authorNameIncorrect)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            update(): 400 status.
            If correct entered authorId and entered size name of author is not valid.
            """)
    void update_admin_when_correctTagId_and_tagNameSize() throws Exception {
        String authorId = "1";

        String authorNameIncorrect = "a";
        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name(authorNameIncorrect)
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            update(): 400 status.
            If not found author by id.
            """)
    void update_admin_when_correctTagId_and_notFoundAuthorById() throws Exception {
        String authorId = "3";

        AuthorDTO authorDTOTesting = AuthorDTO.builder()
                .name("author_name")
                .build();
        String authorDTOJson = objectMapper.writeValueAsString(authorDTOTesting);

        mockMvc.perform(put("/api/v2/author/{id}", authorId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(authorDTOJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of authors.
               """)
    void findAll_foundObjectsByCurrentPage() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/author/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of authors.
               """)
    void findAll_notFoundObjectsByCurrentPage() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/author/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findById(): Return status 200 and found author by entered id.
            """)
    void findById() throws Exception {
        String authorId = "1";

        mockMvc.perform(get("/api/v2/author/{id}", authorId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findById(): Return status 204 if not found author by entered id.
            """)
    void findById_when_notFoundAuthorById() throws Exception {
        String authorId = "3";

        mockMvc.perform(get("/api/v2/author/{id}", authorId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(value = """
            findByPartOfName(): Return status 200 and found list of authors by entered part of name.
            """)
    void findByPartOfName_foundObjectsByCurrentPage() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "ik";

        mockMvc.perform(get("/api/v2/author/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findByPartOfName(): Return status 200 and found list of authors by entered part of name.
            """)
    void findByPartOfName_notFoundObjectsByCurrentPage() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "ik";

        mockMvc.perform(get("/api/v2/author/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findByPartOfName(): Return status 204 if not found list of authors by entered part of name.
            """)
    void findByPartOfName_when_notFoundAuthors() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "partOfName";

        mockMvc.perform(get("/api/v2/author/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(value = """
            findByNewsId(): Return status 200 and found list of authors by entered newsId.
            """)
    void findByNewsId() throws Exception {
        String newsId = "1";

        mockMvc.perform(get("/api/v2/author/news/{newsId}", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findByNewsId(): Return status 204 if not found list of authors by entered newsId.
            """)
    void findByNewsId_when_notFoundContent() throws Exception {
        String newsId = "3";

        mockMvc.perform(get("/api/v2/author/news/{newsId}", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }
}