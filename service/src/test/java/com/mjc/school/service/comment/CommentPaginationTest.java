package com.mjc.school.service.comment;

import com.mjc.school.service.comment.impl.CommentServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentPaginationTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private PaginationService paginationService;

    @Test
    void getPagination() {
        List<CommentDTO> commentDTOList = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .build(),
                CommentDTO.builder().id(5).content("CONTENT 5")
                        .modified("2023-10-20T15:05:25.413")
                        .build(),
                CommentDTO.builder().id(4).content("CONTENT 4")
                        .modified("2023-10-20T14:05:25.413")
                        .build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(anyLong(), anyInt())).thenReturn(2);

        Pagination<CommentDTO> paginationExpected =
                Pagination.<CommentDTO>builder()
                        .entity(commentDTOList)
                        .size(size)
                        .numberPage(page)
                        .countAllEntity(countAllElements)
                        .maxNumberPage(2)
                        .build();
        Pagination<CommentDTO> paginationActual =
                commentService.getPagination(commentDTOList, countAllElements, page, size);
        assertEquals(paginationExpected, paginationActual);
    }
}