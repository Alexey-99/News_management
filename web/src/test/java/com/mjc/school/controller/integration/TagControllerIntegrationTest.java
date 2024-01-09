package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.TagDTO;
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
class TagControllerIntegrationTest {
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
            CreateJwtTokenRequest createJwtTokenRequestAdmin =
                    new CreateJwtTokenRequest("user", "123456");
            CreateJwtTokenRequest createJwtTokenRequestUser =
                    new CreateJwtTokenRequest("user_2", "123456");
            adminJwtToken = authService.createAuthToken(createJwtTokenRequestAdmin);
            userJwtToken = authService.createAuthToken(createJwtTokenRequestUser);
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_roleAdmin() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_sport").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(post("/api/v2/tag")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @Test
    void create_roleUser() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_name").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(post("/api/v2/tag")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void create_roleGuest() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_name").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(post("/api/v2/tag")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }


    @ParameterizedTest
    @MethodSource(value = "providerNotValidTags")
    void create_roleAdmin_and_notValidNews(TagDTO tagDTO) throws Exception {
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(post("/api/v2/tag")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_roleAdmin() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_update").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(put("/api/v2/tag/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void update_roleUser() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_action").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(put("/api/v2/tag/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void update_roleGuest() throws Exception {
        TagDTO tagDTO = TagDTO.builder()
                .name("tag_action").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(put("/api/v2/tag/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidTags")
    void update_roleAdmin_and_notValidNews(TagDTO tagDTO) throws Exception {
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        mockMvc.perform(put("/api/v2/tag/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken)
                        .content(tagDTOJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidTags() {
        return List.of(
                Arguments.of(TagDTO.builder()
                        .name(null)
                        .build()),
                Arguments.of(TagDTO.builder()
                        .name("   ")
                        .build()),
                Arguments.of(TagDTO.builder()
                        .name("ta")
                        .build())
        );
    }

    @Test
    void addToNews_roleAdmin() throws Exception {
        String tagId = "2";
        String newsId = "1";

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void addToNews_roleUser() throws Exception {
        String tagId = "2";
        String newsId = "1";

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void addToNews_roleGuest() throws Exception {
        String tagId = "1";
        String newsId = "1";

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addToNews_roleAdmin_and_notCorrectTagId() throws Exception {
        String tagId = "3";
        String newsId = "1";

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addToNews_roleAdmin_and_correctTagId_and_notCorrectNewsId() throws Exception {
        String tagId = "1";
        String newsId = "3";

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFromNews_roleAdmin() throws Exception {
        String tagId = "1";
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/tag/from-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());

    }

    @Test
    void deleteFromNews_roleUser() throws Exception {
        String tagId = "1";
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/tag/from-news")
                        .param("tag", tagId)
                        .param("news", newsId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFromNews_roleGuest() throws Exception {
        String tagId = "1";
        String newsId = "1";

        mockMvc.perform(delete("/api/v2/tag/from-news")
                        .param("tag", tagId)
                        .param("news", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteById_roleAdmin() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/{id}", tagId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_roleUser() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/{id}", tagId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById_roleGuest() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/{id}", tagId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteFromAllNews_roleAdmin() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/all-news/{id}", tagId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFromAllNews_roleUser() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/all-news/{id}", tagId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteFromAllNews_roleGuest() throws Exception {
        String tagId = "1";

        mockMvc.perform(delete("/api/v2/tag/all-news/{id}", tagId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void findAllWithoutPagination() throws Exception {
        mockMvc.perform(get("/api/v2/tag/all"))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findAllWithPagination_foundObjectsByCurrentPage() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/tag/all/page")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findAllWithPagination_notFoundObjectsByCurrentPage() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/tag/all/page")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_foundTag() throws Exception {
        String tagId = "1";

        mockMvc.perform(get("/api/v2/tag/{id}", tagId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_notFoundTag() throws Exception {
        String tagId = "3";

        mockMvc.perform(get("/api/v2/tag/{id}", tagId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByPartOfName_when_foundTag_foundObjectsByCurrentPage() throws Exception {
        String partOfName = "g_na";

        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";


        mockMvc.perform(get("/api/v2/tag/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByPartOfName_when_foundTag_notFoundObjectsByCurrentPage() throws Exception {
        String partOfName = "g_na";

        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";


        mockMvc.perform(get("/api/v2/tag/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByPartOfName_when_notFoundTag() throws Exception {
        String partOfName = "part";

        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";


        mockMvc.perform(get("/api/v2/tag/part-name/{partOfName}", partOfName)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByNewsId_when_foundTag_foundObjectsByCurrentPage() throws Exception {
        String newsId = "1";

        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/tag/news/{newsId}", newsId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByNewsId_when_foundTag_notFoundObjectsByCurrentPage() throws Exception {
        String newsId = "1";

        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/tag/news/{newsId}", newsId)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByNewsId_when_notFoundTag() throws Exception {
        String newsId = "2";

        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/tag/news/{newsId}", newsId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }
}