package com.mjc.school.service.tag;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteFromAllNewsTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;

    @Test
    void deleteFromAllNews_when_tagExistsById() throws ServiceBadRequestParameterException {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(true);
        boolean actualResult = tagService.deleteFromAllNews(tagId);
        assertTrue(actualResult);
    }

    @Test
    void deleteFromAllNews_when_tagNotExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(false);
        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.deleteFromAllNews(tagId));
        assertEquals("service.exception.not_found_tag_by_id", exceptionActual.getMessage());
    }
}