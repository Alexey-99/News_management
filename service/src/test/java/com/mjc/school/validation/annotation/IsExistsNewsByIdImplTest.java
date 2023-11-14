package com.mjc.school.validation.annotation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.validation.annotation.impl.IsExistsNewsByIdImpl;
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
class IsExistsNewsByIdImplTest {
    @InjectMocks
    private IsExistsNewsByIdImpl isExistsNewsById;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Test
    void isValid_when_existsNews() {
        long newsId = 1L;
        when(newsRepository.existsById(newsId)).thenReturn(true);
        boolean actualResult = isExistsNewsById.isValid(newsId, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notExistsNews() {
        long newsId = 1L;
        when(newsRepository.existsById(newsId)).thenReturn(false);
        boolean actualResult = isExistsNewsById.isValid(newsId, constraintValidatorContext);
        assertFalse(actualResult);
    }
}