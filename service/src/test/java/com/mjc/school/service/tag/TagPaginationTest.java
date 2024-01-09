package com.mjc.school.service.tag;

import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagPaginationTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private PaginationService paginationService;

    @Test
    void getPagination() {
        List<TagDTO> tagDTOList = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(4).name("D_tag_name").build(),
                TagDTO.builder().id(5).name("Z_tag_name").build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 2;

        when(paginationService.calcMaxNumberPage(countAllElements, size))
                .thenReturn(maxNumberPageExpected);

        Pagination<TagDTO> tagDTOPaginationExpected = Pagination.<TagDTO>builder()
                .entity(tagDTOList)
                .size(size)
                .numberPage(page)
                .countAllEntity(countAllElements)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        Pagination<TagDTO> tagDTOPaginationActual =
                tagService.getPagination(tagDTOList, countAllElements, page, size);
        assertEquals(tagDTOPaginationExpected, tagDTOPaginationActual);
    }
}