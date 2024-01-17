package com.mjc.school.controller.integration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
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
class ValidAccessTokenIsUserTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 200 and true.
            If correct token for role USER.
            """)
    void validAuthTokenUserRole_when_validToken_withRoleUser() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequestUser = new CreateJwtTokenRequest("user_2", "123456");
        JwtTokenResponse jwtTokenResponse = authService.createAuthToken(createJwtTokenRequestUser);

        ValidationJwtToken validationJwtToken = new ValidationJwtToken(jwtTokenResponse.getAccessToken());
        String validationJwtTokenRequestJson = objectMapper.writeValueAsString(validationJwtToken);

        mockMvc.perform(post("/api/v2/auth/token/valid/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(validationJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 200 and true.
            If correct token for role USER.
            """)
    void validAuthTokenUserRole_when_validToken_withRoleAdmin() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequestUser = new CreateJwtTokenRequest("user", "123456");
        JwtTokenResponse jwtTokenResponse = authService.createAuthToken(createJwtTokenRequestUser);
        ValidationJwtToken validationJwtToken = new ValidationJwtToken(jwtTokenResponse.getAccessToken());
        String validationJwtTokenRequestJson = objectMapper.writeValueAsString(validationJwtToken);

        mockMvc.perform(post("/api/v2/auth/token/valid/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(validationJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 401 and false.
            If not correct token for role USER.
            """)
    void validAuthTokenUserRole_when_notValidToken() throws Exception {
        ValidationJwtToken validationJwtToken = new ValidationJwtToken(null);
        String validationJwtTokenRequestJson = objectMapper.writeValueAsString(validationJwtToken);

        mockMvc.perform(post("/api/v2/auth/token/valid/user")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(validationJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }
}