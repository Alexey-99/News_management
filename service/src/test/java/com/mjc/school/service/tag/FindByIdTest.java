package com.mjc.school.service.tag;

import com.mjc.school.converter.TagConverter;
import com.mjc.school.exception.ServiceNoContentException;
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
class FindByIdTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagConverter tagConverter;

    @Test
    void findById_when_foundTagById() throws ServiceNoContentException {
        long tagId = 1;

        Tag tagFromDB = Tag.builder().id(1).name("A_tag_name").build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("A_tag_name").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        TagDTO tagDTOActual = tagService.findById(tagId);
        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void findById_when_notFoundTagById() {
        long tagId = 1;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());
        assertThrows(ServiceNoContentException.class,
                () -> tagService.findById(tagId));
    }
}