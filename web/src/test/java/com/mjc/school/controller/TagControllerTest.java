package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@ExtendWith(MockitoExtension.class)
class TagControllerTest {
    @InjectMocks
    private TagController tagController;
    @Mock
    private TagService tagService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            create(): Return status 201.
            If transferred in request correct tagDTO.
            """)
    void create_when_everythingOk() throws Exception {
        TagDTO tagDTO = TagDTO.builder().name("tagName").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);
        when(tagService.create(any(TagDTO.class))).thenReturn(true);

        mockMvc.perform(post("/api/v2/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(true));

        verify(tagService, times(1)).create(tagDTO);
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of tag is not valid. Tag name is null.
            """)
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
                    String messageExpected = "default message [tag_dto.name.not_valid.null]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            If name of tag is not valid. Tag name is blank.
            """)
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
                    String messageExpected = "default message [tag_dto.name.not_valid.is_blank]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            create(): throws MethodArgumentNotValidException.
            if size name of tag is not valid.
            """)
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
                    String messageExpected = "default message [tag_dto.name.not_valid.size]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            addToNews(): Return status 200.
            If correct entered tagId and newsId.
            """)
    void addToNews_when_everythingOk() throws Exception {
        String tagId = "1";
        String newsId = "1";

        when(tagService.addToNews(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(put("/api/v2/tag/to-news")
                        .param("tag", tagId)
                        .param("news", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName(value = """
            deleteFromNews(): Return status 200.
            If entered correct tagId and newsId.""")
    void deleteFromNews_when_everythingOk() throws Exception {
        String tagId = "1";
        String newsId = "1";

        when(tagService.deleteFromNews(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/tag/from-news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("tag", tagId)
                        .param("news", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName(value = """
            deleteById: Return status 200.
            if correct entered tagId and deleted tag by id
            """)
    void deleteById() throws Exception {
        String tagId = "1";

        when(tagService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/tag/{id}", tagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName(value = """
            deleteById: Return status 200.
            If correct entered tagId and deleted tag by id
            """)
    void deleteFromAllNews() throws Exception {
        String tagId = "1";

        when(tagService.deleteFromAllNews(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/tag/all-news/{id}", tagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName(value = """
            deleteById: Return status 200 and updated tag.
            If correct entered tagId and tagDTO.
            """)
    void update_when_everythingOk() throws Exception {
        String tagId = "1";

        TagDTO tagDTO = TagDTO.builder().name("tag_name").build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        TagDTO tagDTOUpdated = TagDTO.builder()
                .name(tagDTO.getName())
                .build();

        when(tagService.update(any(TagDTO.class))).thenReturn(tagDTOUpdated);

        mockMvc.perform(put("/api/v2/tag/{id}", tagId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(tagDTOUpdated));
    }

    @Test
    @DisplayName(value = """
            deleteById: Return status 200 and updated tag.
            If correct entered tagId and entered name of tag is not valid. Tag name is null.
            """)
    void update_when_correctTagId_and_tagNameNull() throws Exception {
        String tagId = "1";

        TagDTO tagDTO = TagDTO.builder().name(null).build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        mockMvc.perform(put("/api/v2/tag/{id}", tagId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [tag_dto.name.not_valid.null]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
    }

    @Test
    @DisplayName(value = """
            deleteById: Return status 200 and updated tag.
            If correct entered tagId and entered name of tag is not valid. Tag name is null.
            """)
    void update_when_correctTagId_and_tagNameBlank() throws Exception {
        String tagId = "1";

        TagDTO tagDTO = TagDTO.builder().name(null).build();
        String tagDTOJson = objectMapper.writeValueAsString(tagDTO);

        mockMvc.perform(put("/api/v2/tag/{id}", tagId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof MethodArgumentNotValidException);
                    String messageExpected = "default message [tag_dto.name.not_valid.null]";
                    assertTrue(exception.getMessage().contains(messageExpected));
                });
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