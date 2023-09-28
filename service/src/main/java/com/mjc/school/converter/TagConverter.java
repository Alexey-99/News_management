package com.mjc.school.converter;

import com.mjc.school.entity.Tag;
import com.mjc.school.validation.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {
    public Tag toTag(TagDTO tagDTO) {
        return new Tag.TagBuilder()
                .setId(tagDTO.getId())
                .setName(tagDTO.getName())
                .setNews(tagDTO.getNews())
                .build();
    }

    public TagDTO toTagDTO(Tag tag) {
        return new TagDTO.TagDTOBuilder()
                .setId(tag.getId())
                .setName(tag.getName())
                .setNews(tag.getNews())
                .build();
    }
}