package com.mjc.school.service.news;

import com.mjc.school.service.news.impl.NewsServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.NewsDTO;
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
class PaginationNewsTest {
    @InjectMocks
    private NewsServiceImpl newsService;
    @Mock
    private PaginationService paginationService;

    @Test
    void getPagination() {
        List<NewsDTO> newsDTOList = List.of(
                NewsDTO.builder().id(2).build(),
                NewsDTO.builder().id(1).build(),
                NewsDTO.builder().id(3).build(),
                NewsDTO.builder().id(4).build(),
                NewsDTO.builder().id(5).build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(anyLong(), anyInt()))
                .thenReturn(2);

        Pagination<NewsDTO> newsDTOPaginationExpected = Pagination.<NewsDTO>builder()
                .entity(newsDTOList)
                .size(size)
                .numberPage(page)
                .countAllEntity(countAllElements)
                .maxNumberPage(2)
                .build();
        Pagination<NewsDTO> newsDTOPaginationActual =
                newsService.getPagination(newsDTOList, countAllElements, page, size);
        assertEquals(newsDTOPaginationExpected, newsDTOPaginationActual);
    }
}