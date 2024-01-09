package com.mjc.school.service.author;

import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorPaginationTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private PaginationService paginationService;

    @Test
    void getPagination() {
        List<AuthorDTO> authorDTOList = List.of(
                AuthorDTO.builder().id(2).countNews(6).build(),
                AuthorDTO.builder().id(1).countNews(4).build(),
                AuthorDTO.builder().id(3).countNews(2).build(),
                AuthorDTO.builder().id(4).countNews(0).build(),
                AuthorDTO.builder().id(5).countNews(0).build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(countAllElements, size)).thenReturn(2);

        Pagination<AuthorDTO> authorDTOPaginationExpected = Pagination.<AuthorDTO>builder()
                .entity(authorDTOList)
                .size(size)
                .numberPage(page)
                .countAllEntity(countAllElements)
                .maxNumberPage(2)
                .build();
        Pagination<AuthorDTO> authorDTOPaginationActual =
                authorService.getPagination(authorDTOList, countAllElements, page, size);
        assertEquals(authorDTOPaginationExpected, authorDTOPaginationActual);
    }
}