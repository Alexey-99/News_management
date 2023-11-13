package com.mjc.school.converter;

import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.model.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagConverterTest {
    @InjectMocks
    private TagConverter tagConverter;
    private static Tag tagExpected;
    private static Tag tagActual;
    private static TagDTO tagDTO;

    @Test
    void fromDTO() {
        tagDTO = TagDTO.builder()
                .id(1)
                .name("Tag_name_test")
                .build();
        tagExpected = Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
        tagActual = tagConverter.fromDTO(tagDTO);
        assertEquals(tagExpected, tagActual);
    }

    @Test
    void toDTO() {
    }

    @AfterAll
    static void afterAll() {
        tagDTO = null;
        tagActual = null;
        tagExpected = null;
    }
}