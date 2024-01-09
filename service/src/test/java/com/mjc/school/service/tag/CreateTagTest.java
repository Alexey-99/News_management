package com.mjc.school.service.tag;

import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import com.mjc.school.validation.dto.TagDTO;
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
class CreateTagTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;

    @Test
    void create_when_tagNotExistsByName() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name_test").build();
        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(true);
        when(tagConverter.fromDTO(tagDTOTesting))
                .thenReturn(Tag.builder().id(1).name("tag_name_test").build());
        assertTrue(tagService.create(tagDTOTesting));
    }

    @Test
    void create_when_tagExistsByName() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name_test").build();
        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(false);
        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.create(tagDTOTesting));
        assertEquals("tag_dto.name.not_valid.exists_tag_by_name",
                exceptionActual.getMessage());
    }
}