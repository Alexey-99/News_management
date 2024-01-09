package com.mjc.school.service.tag;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteByIdTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;

    @Test
    void deleteById_when_tagExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(true);
        boolean actualResult = tagService.deleteById(tagId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_tagNotExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(false);
        boolean actualResult = tagService.deleteById(tagId);
        assertTrue(actualResult);
    }
}