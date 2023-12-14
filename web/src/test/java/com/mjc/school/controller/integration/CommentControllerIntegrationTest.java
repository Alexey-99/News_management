package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.CommentDTO;
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
class CommentControllerIntegrationTest {
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
    void create_roleAdmin() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("comment_content")
                .newsId(1)
                .build();
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(post("/api/v2/comment")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleUser() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("comment_content")
                .newsId(1)
                .build();
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(post("/api/v2/comment")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleGuest() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("comment_content")
                .newsId(1)
                .build();
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(post("/api/v2/comment")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidComments")
    void create_roleUser_and_notValidComments(CommentDTO commentDTO) throws Exception {
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(post("/api/v2/comment")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }


    @Test
    void update_roleAdmin() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("comment_content_other")
                .newsId(1)
                .build();
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(put("/api/v2/comment/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void update_roleUser() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("comment_content_other")
                .newsId(1)
                .build();
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(put("/api/v2/comment/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidComments")
    void update_roleGuest_notValidComments(CommentDTO commentDTO) throws Exception {
        String commentDTOJson = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(put("/api/v2/comment/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(commentDTOJson)
                )
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidComments() {
        return List.of(
                Arguments.of(CommentDTO.builder()
                        .content(null)
                        .newsId(1)
                        .build()),
                Arguments.of(CommentDTO.builder()
                        .content("   ")
                        .newsId(1)
                        .build()),
                Arguments.of(CommentDTO.builder()
                        .content("qw")
                        .newsId(1)
                        .build()),
                Arguments.of(CommentDTO.builder()
                        .content("qwerty")
                        .newsId(-2)
                        .build()),
                Arguments.of(CommentDTO.builder()
                        .content("qwerty")
                        .newsId(3)
                        .build())
        );
    }

    @Test
    void deleteById_roleUser() throws Exception {
        String commentId = "1";

        mockMvc.perform(delete("/api/v2/comment/{id}", commentId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById_roleAdmin() throws Exception {
        String commentId = "1";

        mockMvc.perform(delete("/api/v2/comment/{id}", commentId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_roleGuest() throws Exception {
        String commentId = "1";

        mockMvc.perform(delete("/api/v2/comment/{id}", commentId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteById_roleAdmin_withNotExistsComment() throws Exception {
        String commentId = "2";

        mockMvc.perform(delete("/api/v2/comment/{id}", commentId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByNewsId_roleAdmin() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/comment/news/{newsId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByNewsId_roleUser() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/comment/news/{newsId}", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteByNewsId_roleGuest() throws Exception {
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/comment/news/{newsId}", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findAll() throws Exception {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/comment/all")
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByNewsId_when_foundComments() throws Exception {
        String newsId = "1";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/comment/news/{newsId}", newsId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByNewsId_when_notFoundComments() throws Exception {
        String newsId = "2";

        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        mockMvc.perform(get("/api/v2/comment/news/{newsId}", newsId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findById_when_foundComment() throws Exception {
        String commentId = "1";

        mockMvc.perform(get("/api/v2/comment/{id}", commentId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_notFoundComment() throws Exception {
        String commentId = "2";

        mockMvc.perform(get("/api/v2/comment/{id}", commentId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }
}