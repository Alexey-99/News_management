package com.mjc.school.controller;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
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
    void create(AuthorDTO authorDTO) throws ServiceException, IncorrectParameterException {
    }

    @Test
    @DisplayName(value = """
                        
            """)
    void deleteById(long id) throws ServiceException, IncorrectParameterException {
    }

    @Test
    @DisplayName(value = """
                        
            """)
    void update(AuthorDTO authorDTO) throws ServiceException, IncorrectParameterException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void findAllAuthors(long countElementsReturn, long numberPage) throws ServiceException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void findById(long id) throws ServiceException, IncorrectParameterException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void findByPartOfName(String partOfName, long countElementsReturn, long numberPage) throws ServiceException, IncorrectParameterException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void findByNewsId(long newsId) throws ServiceException, IncorrectParameterException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void selectAllAuthorsWithAmountOfWrittenNews(long countElementsReturn, long numberPage) throws ServiceException {

    }

    @Test
    @DisplayName(value = """
                        
            """)
    void sortAllAuthorsIdWithAmountOfWrittenNewsDesc(long countElementsReturn, long numberPage) throws ServiceException {
    }
}