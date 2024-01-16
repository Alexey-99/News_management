package com.mjc.school.service.tag;

import com.mjc.school.converter.TagConverter;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTagTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;

    @Test
    void update_when_notFoundTagById() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).build();

        when(tagRepository.findById(tagDTOTesting.getId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.update(tagDTOTesting));

        assertEquals("service.exception.not_found_tag_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundTagById_and_equalTagNames() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("tag_name").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        TagDTO tagDTOActual = tagService.update(tagDTOTesting);

        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void update_when_foundTagById_and_notEqualTagNames_and_tagNotExistsByName() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name_other").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("tag_name_other").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(true);

        TagDTO tagDTOActual = tagService.update(tagDTOTesting);

        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void update_when_foundTagById_and_notEqualTagNames_and_tagExistsByName() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name_other").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.update(tagDTOTesting));

        assertEquals("tag_dto.name.not_valid.exists_tag_by_name", exceptionActual.getMessage());
    }
}