package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class RoleControllerIntegrationTest {
    private static final String AUTHORIZATION_HEADER_VALUE_START_WITH = "Bearer ";
    private String adminJwtToken = null;
    private String userJwtToken = null;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
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
            findAll(): Return status 403 and List of roles.
            If role is user.
               """)
    void findAll_roleUser() throws Exception {

        mockMvc.perform(get("/api/v2/role/all")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of roles.
            If role is admin.
               """)
    void findAll_roleAdmin() throws Exception {
        mockMvc.perform(get("/api/v2/role/all")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 401 and List of roles.
            If role is guest.
               """)
    void findAll_roleGuest() throws Exception {
        mockMvc.perform(get("/api/v2/role/all")
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }
}
