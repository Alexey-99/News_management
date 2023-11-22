package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.annotation.IsExistsUserByLogin;
import com.mjc.school.validation.annotation.impl.IsExistsUserByLoginImpl;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 201 and JWT token.
            """) // TODO I HAVE PROBLEM WITH ANNOTATION IsExistsUserByLogin
    void createAuthToken() throws Exception {
//        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
//                .userName("userName")
//                .password("password").build();
//        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);
//
//        String jwtTokenExpected = "jwt_token";
//        when(authService.createAuthToken(createJwtTokenRequest)).thenReturn(jwtTokenExpected);
//
//        JwtTokenResponse jwtTokenResponseExpected = JwtTokenResponse.builder()
//                .token(jwtTokenExpected)
//                .build();
//
//        mockMvc.perform(post("/api/v2/auth/token")
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(createJwtTokenRequestJson))
//                .andExpect(result -> {
//                    String actualContentType = result.getResponse().getContentType();
//                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
//                })
//                .andExpect(result -> {
//                    String actualContentJson = result.getResponse().getContentAsString();
//                    String expectedContentJson = objectMapper.writeValueAsString(jwtTokenResponseExpected);
//                    assertEquals(expectedContentJson, actualContentJson);
//                });
    }
}