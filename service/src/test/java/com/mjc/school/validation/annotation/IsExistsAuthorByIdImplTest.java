package com.mjc.school.validation.annotation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.annotation.impl.IsExistsAuthorByIdImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsExistsAuthorByIdImplTest {
    @InjectMocks
    private IsExistsAuthorByIdImpl isExistsAuthorById;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Test
    void isValid_when_existsAuthor() {
        long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(true);
        boolean actualResult = isExistsAuthorById.isValid(authorId, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notExistsAuthor() {
        long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(false);
        boolean actualResult = isExistsAuthorById.isValid(authorId, constraintValidatorContext);
        assertFalse(actualResult);
    }
}