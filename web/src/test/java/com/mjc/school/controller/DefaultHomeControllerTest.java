package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.handler.DateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DefaultHomeControllerTest {
    @InjectMocks
    private DefaultHomeController homeController;
    @Mock
    private DateHandler dateHandler;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void error404() throws Exception {
        ErrorResponse errorResponseExpected = ErrorResponse.builder()
                .errorMessage("Everything happened is not so")
                .errorCode(NOT_FOUND.value())
                .timestamp("date-time")
                .build();

        when(dateHandler.getCurrentDate()).thenReturn("date-time");

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(errorResponseExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }
}