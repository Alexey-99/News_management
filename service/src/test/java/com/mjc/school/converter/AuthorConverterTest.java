package com.mjc.school.converter;

import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.validation.dto.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthorConverterTest {
    @InjectMocks
    private AuthorConverter authorConverter;

    @Test
    void fromDTO() {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1).name("Tom").build();
        Author authorExpected = Author.builder().id(1).name("Tom").build();
        Author authorActual = authorConverter.fromDTO(authorDTOTesting);
        assertEquals(authorExpected, authorActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerAuthorsParams")
    void toDTO(Author author, AuthorDTO authorDTOExpected) {
        AuthorDTO authorDTOActual = authorConverter.toDTO(author);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    static List<Arguments> providerAuthorsParams() {
        return List.of(
                Arguments.of(
                        Author.builder()
                                .id(1)
                                .name("Tom")
                                .news(null).build(),
                        AuthorDTO.builder()
                                .id(1)
                                .name("Tom")
                                .countNews(0)
                                .build()),
                Arguments.of(
                        Author.builder()
                                .id(1)
                                .name("Tom")
                                .news(List.of(
                                        News.builder().id(1).build(),
                                        News.builder().id(2).build()))
                                .build(),
                        AuthorDTO.builder()
                                .id(1)
                                .name("Tom")
                                .countNews(2)
                                .build()),
                Arguments.of(
                        Author.builder()
                                .id(1)
                                .name("Tom")
                                .news(List.of())
                                .build(),
                        AuthorDTO.builder()
                                .id(1)
                                .name("Tom")
                                .countNews(0)
                                .build()));
    }
}