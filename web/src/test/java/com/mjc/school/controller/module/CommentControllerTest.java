package com.mjc.school.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.CommentController;
import com.mjc.school.service.comment.CommentService;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @InjectMocks
    private CommentController commentController;
    @Mock
    private CommentService commentService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deleteById() throws Exception {
        String commentId = "1";

        when(commentService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/comment/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        long commentIdL = Long.parseLong(commentId);
        verify(commentService, times(1)).deleteById(commentIdL);
    }

    @Test
    void deleteByNewsId() throws Exception {
        String newsId = "1";

        when(commentService.deleteByNewsId(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/v2/comment/news/{newsId}", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        long newsIdL = Long.parseLong(newsId);
        verify(commentService, times(1)).deleteByNewsId(newsIdL);
    }

    @Test
    void findAll() throws Exception {
        int maxNumberPageExpected = 1;
        long countAllElementsExpected = 3;
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        List<CommentDTO> commentsFindAllList = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(commentService.findAll(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(commentsFindAllList);

        when(commentService.countAllComments()).thenReturn(countAllElementsExpected);

        Pagination<CommentDTO> newsDTOPaginationExpected = Pagination.<CommentDTO>builder()
                .entity(commentsFindAllList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(commentService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/comment/all")
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    void findByNewsId() throws Exception {
        String newsId = "1";
        long newsIdL = Long.parseLong(newsId);

        int maxNumberPageExpected = 1;
        long countAllElementsExpected = 3;
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        List<CommentDTO> commentsFindAllList = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .newsId(newsIdL)
                        .modified("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .newsId(newsIdL)
                        .modified("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .newsId(newsIdL)
                        .modified("2023-10-20T16:05:25.413").build());

        when(commentService.findByNewsId(anyLong(), anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(commentsFindAllList);

        when(commentService.countAllCommentsByNewsId(anyLong()))
                .thenReturn(countAllElementsExpected);

        Pagination<CommentDTO> newsDTOPaginationExpected = Pagination.<CommentDTO>builder()
                .entity(commentsFindAllList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        when(commentService.getPagination(anyList(), anyLong(), anyInt(), anyInt()))
                .thenReturn(newsDTOPaginationExpected);

        mockMvc.perform(get("/api/v2/comment/news/page/{newsId}", newsId)
                        .requestAttr("size", size)
                        .requestAttr("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(newsDTOPaginationExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }

    @Test
    void findById() throws Exception {
        String commentId = "1";

        long commentIdL = Long.parseLong(commentId);
        CommentDTO commentDTOExpected =  CommentDTO.builder()
                .id(commentIdL)
                .content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build();
        when(commentService.findById(anyLong())).thenReturn(commentDTOExpected);

        mockMvc.perform(get("/api/v2/comment/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(commentDTOExpected);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }
}