package com.mjc.school.converter;

import com.mjc.school.model.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {
    public Tag fromDTO(TagDTO tagDTO) {
        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
    }

    public TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .countNews(tag.getNews() != null
                        ? tag.getNews().size()
                        : 0)
                .build();
    }
}