package com.mjc.school.service.author;

import com.mjc.school.converter.AuthorConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.Author;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.validation.dto.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAuthorTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;

    @Test
    void create_when_authorNotExistsByName() throws ServiceBadRequestParameterException {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        Author authorConverted = Author.builder()
                .id(authorDTOTesting.getId())
                .name(authorDTOTesting.getName())
                .build();
        when(authorConverter.fromDTO(any(AuthorDTO.class)))
                .thenReturn(authorConverted);

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);
        boolean actualResult = authorService.create(authorDTOTesting);
        assertTrue(actualResult);
    }

    @Test
    void create_when_authorExistsByName() {
        AuthorDTO authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.create(authorDTOTesting)
        );
        assertEquals("tag_dto.name.not_valid.exists_tag_by_name", exception.getMessage());
    }
}