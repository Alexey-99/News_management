package com.mjc.school.converter;

import com.mjc.school.entity.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<TagDTO, Tag> {

    @Override
    public Tag fromDTO(TagDTO tagDTO) {
        return new Tag.TagBuilder()
                .setId(tagDTO.getId())
                .setName(tagDTO.getName())
                .build();
    }

    public TagDTO toDTO(Tag tag) {
        return new TagDTO.TagDTOBuilder()
                .setId(tag.getId())
                .setName(tag.getName())
                .setCountNews(tag.getNews().size())
                .build();
    }
}