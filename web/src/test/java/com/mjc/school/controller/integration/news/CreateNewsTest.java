package com.mjc.school.controller.integration.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import lombok.extern.log4j.Log4j2;
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

import static org.apache.logging.log4j.Level.DEBUG;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class CreateNewsTest {
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
                .authorName("Tom")
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleUser() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title")
                .content("news_content")
                .authorName("Tom")
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(newsDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleGuest() throws Exception {
        NewsDTO newsDTO = NewsDTO.builder()
                .title("news_title")
                .content("news_content")
                .authorName("name")
                .build();
        String newsDTOJson = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/api/v2/news")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(newsDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
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
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidNews() {
        return List.of(
                Arguments.of(NewsDTO.builder()
                        .title(null)
                        .content("news_content")
                        .authorName("Tom")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("   ")
                        .content("news_content")
                        .authorName("Tom")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news")
                        .content("news_content")
                        .authorName("Tom")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content(null)
                        .authorName("Tom")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("   ")
                        .authorName("Tom")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("cont")
                        .authorName(null)
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("news_content")
                        .authorName("   ")
                        .build()),
                Arguments.of(NewsDTO.builder()
                        .title("news_title")
                        .content("news_content")
                        .authorName("Alex")
                        .build())
        );
    }
}