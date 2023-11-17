package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.dto.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {
    @InjectMocks
    private TagController tagController;
    @Mock
    private TagService tagService;
    @Mock
    private DateHandler dateHandler;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_when_everythingOk() throws Exception {
        TagDTO tagDTO = TagDTO.builder().name("tagName").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        when(tagService.create(tagDTO)).thenReturn(true);

        mockMvc.perform(post("/api/v2/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(true));

        verify(tagService, times(1)).create(tagDTO);
    }

    @Test
    @DisplayName(value = "throws exception if name of tag is not valid. Tag name is null")
    void create_when_notCorrectEnteredTagNameByNotNull() throws Exception {
        TagDTO tagDTO = TagDTO.builder().name(null).build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        mockMvc.perform(post("/api/v2/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    assertTrue(exception.getMessage().contains("default message [tag_dto.name.not_valid.null]"));
                });
    }

    @Test
    @DisplayName(value = "throws exception if name of tag is not valid. Tag name is blank")
    void create_when_inCorrectEnteredTagNameByNotBlank() throws Exception {
        String incorrectTagName = "   ";
        TagDTO tagDTO = TagDTO.builder().name(incorrectTagName).build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        mockMvc.perform(post("/api/v2/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    assertTrue(exception.getMessage().contains("default message [tag_dto.name.not_valid.is_blank]"));
                });
    }

    @Test
    @DisplayName(value = "throws exception if name size of tag is not valid")
    void create_when_inCorrectEnteredTagNameBySize() throws Exception {
        String incorrectTagName = "t";
        TagDTO tagDTO = TagDTO.builder().name(incorrectTagName).build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        mockMvc.perform(post("/api/v2/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    assertTrue(exception.getMessage().contains("default message [tag_dto.name.not_valid.size]"));
                });
    }

    @Test
    void addToNews() {
    }

    @Test
    void deleteFromNews() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteFromAllNews() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll() throws Exception {
//        mockMvc.perform(get("/api/v2/tag/all")).andExpect(status().isOk());
    }

    @Test
    void findById() {
    }

    @Test
    void findByPartOfName() {
    }

    @Test
    void findByNewsId() {
    }
}