package com.mjc.school.service;

import com.mjc.school.converter.impl.CommentConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.handler.DateHandler;
import com.mjc.school.model.Comment;
import com.mjc.school.model.News;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.comment.impl.CommentServiceImpl;
import com.mjc.school.service.comment.impl.sort.CommentSortField;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.CommentDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentConverter commentConverter;
    @Mock
    private PaginationService paginationService;
    @Mock
    private DateHandler dateHandler;

    @Test
    void create_when_newsNotFoundById() {
        long newsId = 2;
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.fromDTO(commentDTOTesting)).thenReturn(Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build());
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.create(commentDTOTesting));
        assertEquals("service.exception.not_exists_news_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void create_when_newsFoundById() throws ServiceBadRequestParameterException {
        long newsId = 2;
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(newsId)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(dateHandler.getCurrentDate()).thenReturn("date-time");
        when(commentConverter.fromDTO(commentDTOTesting)).thenReturn(Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build());
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.of(News.builder().id(newsId).build()));

        CommentDTO resultActual = commentService.create(commentDTOTesting);
        //assertTrue(resultActual);
    }

    @Test
    void update_when_notFoundCommentById() {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentRepository.findById(commentDTOTesting.getId()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.update(commentDTOTesting));
        assertEquals("service.exception.not_found_comment_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void update_when_foundCommentById_and_notFoundNewsById() {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentRepository.findById(commentDTOTesting.getId()))
                .thenReturn(Optional.of(Comment.builder()
                        .id(commentDTOTesting.getId())
                        .content(commentDTOTesting.getContent())
                        .newsId(commentDTOTesting.getNewsId())
                        .modified(commentDTOTesting.getModified())
                        .created(commentDTOTesting.getCreated())
                        .build()));
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException serviceBadRequestParameterExceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> commentService.update(commentDTOTesting));
        assertEquals("service.exception.not_exists_news_by_id",
                serviceBadRequestParameterExceptionActual.getMessage());
    }

    @Test
    void update_when_foundCommentById_and_foundNewsById() throws ServiceBadRequestParameterException {
        CommentDTO commentDTOTesting = CommentDTO.builder()
                .id(1)
                .content("comment content test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();

        Comment commentFromDB = Comment.builder()
                .id(commentDTOTesting.getId())
                .content(commentDTOTesting.getContent())
                .news(News.builder().id(3).build())
                .newsId(commentDTOTesting.getNewsId())
                .modified(commentDTOTesting.getModified())
                .created(commentDTOTesting.getCreated())
                .build();
        when(commentRepository.findById(commentDTOTesting.getId()))
                .thenReturn(Optional.of(commentFromDB));

        when(dateHandler.getCurrentDate()).thenReturn("date-time");

        News newsFromDB = News.builder().id(commentDTOTesting.getNewsId()).build();
        when(newsRepository.findById(commentDTOTesting.getNewsId()))
                .thenReturn(Optional.of(newsFromDB));

        commentFromDB.setNews(newsFromDB);
        CommentDTO commentDTOExpected = CommentDTO.builder()
                .id(1)
                .content("comment content updated test")
                .newsId(2)
                .created("created date-time")
                .modified("modified date-time")
                .build();
        when(commentConverter.toDTO(commentFromDB))
                .thenReturn(commentDTOExpected);

        CommentDTO commentDTOActual = commentService.update(commentDTOTesting);
        assertEquals(commentDTOExpected, commentDTOActual);
    }

    @Test
    void deleteById_when_commentExistsById() {
        long commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(true);
        boolean actualResult = commentService.deleteById(commentId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_commentNotExistsById() {
        long commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(false);
        boolean actualResult = commentService.deleteById(commentId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByNewsId_when_newsExistsById() {
        long newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(true);
        boolean actualResult = commentService.deleteByNewsId(newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteByNewsId_when_newsNotExistsById() {
        long newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(false);
        boolean actualResult = commentService.deleteByNewsId(newsId);
        assertTrue(actualResult);
    }

    @Test
    void findAllWithPages_when_foundComments() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        List<Comment> commentsFindAllList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685").build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413").build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413").build());
        when(commentRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(commentsFindAllList);
        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);
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

    @Test
    void findAllWithPages_when_notFoundComments() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(commentRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(List.of());
        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);


        ServiceNoContentException exceptionActual =
                assertThrows(ServiceNoContentException.class,
                        () -> commentService.findAll(page, size, sortField, sortType));
        assertEquals("service.exception.not_found_comments",
                exceptionActual.getMessage());
    }

    @Test
    void findAllComments_when_foundComments() {
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
    void findAllComments_when_notFoundComments() {
        when(commentRepository.findAll()).thenReturn(List.of());
        List<CommentDTO> commentDTOListExpected = List.of();
        List<CommentDTO> commentDTOListActual = commentService.findAll();
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    @Test
    void countAllComments() {
        when(commentRepository.countAllComments()).thenReturn(3L);
        long countAllCommentsExpected = 3;
        long countAllCommentsActual = commentService.countAllComments();
        assertEquals(countAllCommentsExpected, countAllCommentsActual);
    }

    @Test
    void findByNewsId_when_foundCommentsByNewsId() throws ServiceNoContentException {
        long newsId = 2;
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        List<Comment> commentsFindAllList = List.of(
                Comment.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .news(News.builder().id(newsId).build())
                        .build(),
                Comment.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .news(News.builder().id(newsId).build())
                        .build(),
                Comment.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .news(News.builder().id(newsId).build())
                        .build());
        when(commentRepository.findByNewsId(newsId,
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField)))).thenReturn(commentsFindAllList);
        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(numberFirstElement);
        when(commentConverter.toDTO(Comment.builder().id(1).content("CONTENT 1")
                .modified("2023-10-20T16:05:38.685")
                .news(News.builder().id(newsId).build())
                .build()))
                .thenReturn(CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .newsId(newsId).build());
        when(commentConverter.toDTO(Comment.builder().id(3).content("CONTENT 3")
                .modified("2023-10-20T16:05:32.413")
                .news(News.builder().id(newsId).build())
                .build()))
                .thenReturn(CommentDTO.builder().id(3).content("CONTENT 3")
                        .newsId(newsId).modified("2023-10-20T16:05:32.413").build());
        when(commentConverter.toDTO(Comment.builder().id(2).content("CONTENT 2")
                .modified("2023-10-20T16:05:25.413")
                .news(News.builder().id(newsId).build())
                .build()))
                .thenReturn(CommentDTO.builder().id(2).content("CONTENT 2")
                        .newsId(newsId).modified("2023-10-20T16:05:25.413").build());
        List<CommentDTO> commentDTOListExpected = List.of(
                CommentDTO.builder().id(1).content("CONTENT 1")
                        .modified("2023-10-20T16:05:38.685")
                        .newsId(newsId).build(),
                CommentDTO.builder().id(3).content("CONTENT 3")
                        .modified("2023-10-20T16:05:32.413")
                        .newsId(newsId).build(),
                CommentDTO.builder().id(2).content("CONTENT 2")
                        .modified("2023-10-20T16:05:25.413")
                        .newsId(newsId).build());

        List<CommentDTO> commentDTOListActual =
                commentService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(commentDTOListExpected, commentDTOListActual);
    }

    @Test
    void findByNewsId_when_notFoundCommentsByNewsId() {
        long newsId = 2;
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";
        String sortField = "modified";

        when(commentRepository.findByNewsId(newsId,
                PageRequest.of(numberFirstElement, size, Sort.by(DESC, sortField))))
                .thenReturn(List.of());
        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(numberFirstElement);

        ServiceNoContentException serviceNoContentExceptionActual =
                assertThrows(ServiceNoContentException.class,
                        () -> commentService.findByNewsId(newsId, page, size, sortField, sortType));

        assertEquals("service.exception.not_found_comments_by_news_id",
                serviceNoContentExceptionActual.getMessage());
    }

    @Test
    void countAllCommentsByNewsId() {
        long newsId = 2;
        when(commentRepository.countAllCommentsByNewsId(newsId)).thenReturn(3L);
        long countAllCommentsExpected = 3;
        long countAllCommentsActual = commentService.countAllCommentsByNewsId(newsId);
        assertEquals(countAllCommentsExpected, countAllCommentsActual);
    }

    @Test
    void findById_when_notFoundCommentById() {
        long commentId = 1;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        ServiceNoContentException serviceNoContentExceptionActual =
                assertThrows(ServiceNoContentException.class,
                        () -> commentService.findById(commentId));
        assertEquals("service.exception.not_found_comment_by_id",
                serviceNoContentExceptionActual.getMessage());
    }

    @Test
    void findById_when_foundCommentById() throws ServiceNoContentException {
        long commentId = 1;
        Comment commentFromDB = Comment.builder().id(commentId).build();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentFromDB));
        when(commentConverter.toDTO(commentFromDB))
                .thenReturn(CommentDTO.builder().id(commentId).build());
        CommentDTO commentDTOActual = commentService.findById(commentId);
        CommentDTO commentDTOExpected = CommentDTO.builder().id(commentId).build();
        assertEquals(commentDTOExpected, commentDTOActual);
    }

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

        when(paginationService.calcMaxNumberPage(countAllElements, size)).thenReturn(2);

        Pagination<CommentDTO> paginationExpected =
                Pagination.<CommentDTO>builder()
                        .entity(commentDTOList)
                        .size(size)
                        .numberPage(page)
                        .maxNumberPage(2)
                        .build();
        Pagination<CommentDTO> paginationActual =
                commentService.getPagination(commentDTOList, countAllElements, page, size);
        assertEquals(paginationExpected, paginationActual);
    }

    @Test
    void getOptionalSortField_when_sortFieldIsNull() {
        Optional<CommentSortField> optionalActual = commentService.getOptionalSortField(null);
        assertTrue(optionalActual.isEmpty());
    }

    @Test
    void getOptionalSortField_when_notFoundSortField() {
        Optional<CommentSortField> optionalActual =
                commentService.getOptionalSortField("not_found_sort_field");
        assertTrue(optionalActual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldParams_when_foundSortField")
    void getOptionalSortField_when_foundSortField(String sortField, String sortFieldExpected) {
        String sortFieldActual = commentService.getOptionalSortField(sortField)
                .get()
                .name()
                .toLowerCase();
        assertEquals(sortFieldExpected, sortFieldActual);
    }

    static List<Arguments> providerSortFieldParams_when_foundSortField() {
        return List.of(
                Arguments.of("created", CommentSortField.CREATED.name().toLowerCase()),
                Arguments.of("modified", CommentSortField.MODIFIED.name().toLowerCase())
        );
    }
}