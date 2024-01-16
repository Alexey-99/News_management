package com.mjc.school.service.comment;

import com.mjc.school.converter.CommentConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Comment;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.service.comment.impl.CommentServiceImpl;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentConverter commentConverter;
    @Mock
    private PaginationService paginationService;

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortModifiedDesc")
    void findAllWithPages_when_foundComments_and_sortModifiedDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(commentRepository.findAllSortModifiedDesc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortModifiedDesc() {
        return List.of(
                Arguments.of("modified", "DESC"),
                Arguments.of("modified", null),
                Arguments.of(null, "DESC"),
                Arguments.of(null, null),
                Arguments.of("field", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortModifiedAsc")
    void findAllWithPages_when_foundComments_and_sortModifiedAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(commentRepository.findAllSortModifiedAsc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortModifiedAsc() {
        return List.of(
                Arguments.of("modified", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }


    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCreatedDesc")
    void findAllWithPages_when_foundComments_and_sortCreatedDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());
        when(commentRepository.findAllSortCreatedDesc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .created("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .created("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .created("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCreatedDesc() {
        return List.of(
                Arguments.of("created", "DESC"),
                Arguments.of("created", null),
                Arguments.of("created", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortCreatedAsc")
    void findAllWithPages_when_foundComments_and_sortCreatedAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentRepository.findAllSortCreatedAsc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .created("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .created("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .created("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortCreatedAsc() {
        return List.of(Arguments.of("created", "ASC"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findAllWithPages_when_foundComments_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                Comment.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentRepository.findAllSortIdDesc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .created("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .created("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .created("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdDesc() {
        return List.of(
                Arguments.of("id", "DESC"),
                Arguments.of("id", null),
                Arguments.of("id", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdAsc")
    void findAllWithPages_when_foundComments_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<Comment> commentList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());
        when(commentRepository.findAllSortIdAsc(anyInt(), anyInt()))
                .thenReturn(commentList);

        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .created("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .created("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .created("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .created("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .created("2023-10-20T16:05:25.413").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .created("2023-10-20T16:05:32.413").build());

        List<CommentDTO> commentDTOListActual =
                commentService.findAll(page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(Arguments.of("id", "ASC"));
    }

    @Test
    void findAll_when_foundComments() {
        List<Comment> commentsFindAllList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(commentRepository.findAll()).thenReturn(commentsFindAllList);
        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685").build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413").build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413").build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());

        List<CommentDTO> commentDTOListActual = commentService.findAll();
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    @Test
    void findAll_when_notFoundComments() {
        List<Comment> commentsFindAllList = List.of();
        when(commentRepository.findAll()).thenReturn(commentsFindAllList);

        List<CommentDTO> commentDTOListExpected = List.of();

        List<CommentDTO> commentDTOListActual = commentService.findAll();
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    @Test
    void findAllCommentsWithPagination_when_notFoundComments() {
        int page = 1;
        int size = 5;
        String sortType = "DESC";
        String sortField = "modified";

        when(paginationService.calcNumberFirstElement(anyInt(), anyInt())).thenReturn(0);

        when(commentRepository.findAllSortModifiedDesc(anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> commentService.findAll(page, size, sortField, sortType));
    }

    @Test
    void countAllComments() {
        when(commentRepository.countAllComments()).thenReturn(3L);
        long countAllCommentsExpected = 3;
        long countAllCommentsActual = commentService.countAllComments();
        assertEquals(countAllCommentsExpected, countAllCommentsActual);
    }
}