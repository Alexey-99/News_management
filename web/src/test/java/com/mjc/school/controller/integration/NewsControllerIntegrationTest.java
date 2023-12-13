package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class NewsControllerIntegrationTest {
    private static final String AUTHORIZATION_HEADER_VALUE_START_WITH = "Bearer ";
    @Autowired
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    private String adminJwtToken;
    private String userJwtToken;

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
    void create_roleAdmin() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title")
                .content("news_content")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleUser() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title")
                .content("news_content")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void create_roleGuest() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title")
                .content("news_content")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidNews")
    void create_roleAdmin_and_notValidNews(NewsDTO newsDTO) throws Exception {
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteById_roleAdmin() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/{authorId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_roleUser() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/{authorId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById_roleGuest() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/{authorId}", newsId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteByAuthorId_roleAdmin() throws Exception {
        String authorId = "1";

        mockMvc.perform(delete("/api/v2/news/author/{authorId}", authorId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByAuthorId_roleUser() throws Exception {
        String authorId = "1";

        mockMvc.perform(delete("/api/v2/news/author/{authorId}", authorId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteByAuthorId_roleGuest() throws Exception {
        String authorId = "1";

        mockMvc.perform(delete("/api/v2/news/author/{authorId}", authorId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteAllTagsFromNewsByNewsId_roleAdmin() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/all-tags/{newsId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAllTagsFromNewsByNewsId_roleUser() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/all-tags/{newsId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteAllTagsFromNewsByNewsId_roleGuest() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/news/all-tags/{newsId}", newsId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update_roleAdmin() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title_other")
                .content("news_content_other")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/api/v2/news/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void update_roleUser() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title_other")
                .content("news_content_other")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/api/v2/news/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void update_roleGuest() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title_other")
                .content("news_content_other")
                .authorId(1)
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/api/v2/news/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidNews")
    void update_roleAdmin_and_notValidNews(NewsDTO newsDTO) throws Exception {
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/api/v2/news/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidNews() {
        return List.of(
                Arguments.of(NewsDTO.builder()
                        .title(null)
                        .content("news_content")
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("   ")
                        .content("news_content")
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news")
                        .content("news_content")
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content(null)
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("   ")
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("cont")
                        .authorId(1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("news_content")
                        .authorId(-1)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("news_content")
                        .authorId(3)
                        .build())
        );
    }

    @Test
    void findAll() throws Exception {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/news/all")
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_foundNews() throws Exception {
        String newsId = "1";

        mockMvc.perform(get("/api/v2/news/{id}", newsId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_notFoundNews() throws Exception {
        String newsId = "2";

        mockMvc.perform(get("/api/v2/news/{id}", newsId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findNewsByTagName_when_foundNews() throws Exception {
        String tagName = "tag_name";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/news/tag-name/{tagName}", tagName)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findNewsByTagName_when_notFoundNews() throws Exception {
        String tagName = "other_tag_name";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/news/tag-name/{tagName}", tagName)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findNewsByTagId_when_foundNews() throws Exception {
        String tagId = "1";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/news/tag/{tagId}", tagId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findNewsByTagId_when_notFoundNews() throws Exception {
        String tagId = "2";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/news/tag/{tagId}", tagId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findNewsByAuthorName() {
    }

    @Test
    void findNewsByAuthorId() {
    }

    @Test
    void findNewsByPartOfTitle() {
    }

    @Test
    void findNewsByPartOfContent() {
    }
}