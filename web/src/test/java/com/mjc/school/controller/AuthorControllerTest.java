package com.mjc.school.controller;

import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private AuthorController authorController;

//    @Test
//    @DisplayName(value = """
//            """)
//    void create() {
////        given (что должен вернуть метод?)
//
////        when (вызов тестируемого метода)
//
////        then (тестирование)
//
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void deleteById() {
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void update() {
//    }

    @Test
    @DisplayName(value = """
            """)
    void findAll() throws ServiceException {
//        given (что должен вернуть метод?)

//        when (вызов тестируемого метода)
        ResponseEntity<Pagination<AuthorDTO>> actualResponseEntity =
                authorController.findAll(
                        Long.parseLong(DEFAULT_SIZE),
                        Long.parseLong(DEFAULT_NUMBER_PAGE));
//        then (тестирование)
        assertNotNull(actualResponseEntity);
        assertEquals(OK, actualResponseEntity.getStatusCode());

    }

//    @Test
//    @DisplayName(value = """
//            """)
//    void findById() {
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void findByPartOfName() {
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void findByNewsId() {
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void selectAllAuthorsIdWithAmountOfWrittenNews() {
//    }
//
//    @Test
//    @DisplayName(value = """
//            """)
//    void sortAllAuthorsIdWithAmountOfWrittenNewsDesc() {
//    }
}