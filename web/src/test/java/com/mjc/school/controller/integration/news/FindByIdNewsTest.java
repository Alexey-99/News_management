package com.mjc.school.controller.integration.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class FindByIdNewsTest {
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
            adminJwtToken = authService.createAuthToken(createJwtTokenRequestAdmin).getAccessToken();
            userJwtToken = authService.createAuthToken(createJwtTokenRequestUser).getAccessToken();
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    void findById_when_foundNews() throws Exception {
        String newsId = "1";

        mockMvc.perform(get("/api/v2/news/{id}", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_when_notFoundNews() throws Exception {
        String newsId = "3";

        mockMvc.perform(get("/api/v2/news/{id}", newsId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }
}