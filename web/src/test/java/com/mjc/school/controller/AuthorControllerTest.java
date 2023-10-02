package com.mjc.school.controller;

import com.mjc.school.service.author.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private AuthorController authorController;

    @Test
    @DisplayName(value = """
            """)
    void create() {
//        given (что должен вернуть метод?)

//        when (вызов тестируемого метода)

//        then (тестирование)

    }

    @Test
    @DisplayName(value = """
            """)
    void deleteById() {
    }

    @Test
    @DisplayName(value = """
            """)
    void update() {
    }

    @Test
    @DisplayName(value = """
            """)
    void findAll() {
    }

    @Test
    @DisplayName(value = """
            """)
    void findById() {
    }

    @Test
    @DisplayName(value = """
            """)
    void findByPartOfName() {
    }

    @Test
    @DisplayName(value = """
            """)
    void findByNewsId() {
    }

    @Test
    @DisplayName(value = """
            """)
    void selectAllAuthorsIdWithAmountOfWrittenNews() {
    }

    @Test
    @DisplayName(value = """
            """)
    void sortAllAuthorsIdWithAmountOfWrittenNewsDesc() {
    }
}