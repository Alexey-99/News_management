package com.mjc.school.validation.annotation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.annotation.impl.IsExistsAuthorByNameImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsExistsAuthorByNameImplTest {
    @InjectMocks
    private IsExistsAuthorByNameImpl isExistsAuthorByName;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void isValid_when_existsAuthorByName() {
        String authorName = "authorName_test";
        when(authorRepository.existsByName(anyString())).thenReturn(true);
        boolean actualResult = isExistsAuthorByName.isValid(authorName, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notExistsAuthorByName() {
        String authorName = "authorName_test";
        when(authorRepository.existsByName(anyString())).thenReturn(false);
        boolean actualResult = isExistsAuthorByName.isValid(authorName, constraintValidatorContext);
        assertFalse(actualResult);
    }
}