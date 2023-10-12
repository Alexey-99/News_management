package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<TagDTO, Tag> {

    @Override
    public Tag fromDTO(TagDTO tagDTO) {
        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
    }

    @Override
    public TagDTO toDTO(Tag tag) {
        return new TagDTO.TagDTOBuilder()
                .setId(tag.getId())
                .setName(tag.getName())
                .setCountNews(tag.getNews() != null
                        ? tag.getNews().size()
                        : 0)
                .build();
    }
}