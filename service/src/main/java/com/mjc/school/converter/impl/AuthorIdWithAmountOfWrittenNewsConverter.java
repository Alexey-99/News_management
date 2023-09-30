package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorIdWithAmountOfWrittenNewsConverter
        implements Converter<
        AuthorIdWithAmountOfWrittenNewsDTO,
        AuthorIdWithAmountOfWrittenNews> {

    @Override
    public AuthorIdWithAmountOfWrittenNews fromDTO(AuthorIdWithAmountOfWrittenNewsDTO entityDTO) {
        return new AuthorIdWithAmountOfWrittenNews
                .AuthorIdWithAmountOfWrittenNewsBuilder()
                .setAuthorId(entityDTO.getAuthorId())
                .setAmountOfWrittenNews(
                        entityDTO.getAmountOfWrittenNews())
                .build();
    }

    @Override
    public AuthorIdWithAmountOfWrittenNewsDTO toDTO(AuthorIdWithAmountOfWrittenNews entity) {
        return new AuthorIdWithAmountOfWrittenNewsDTO
                .AuthorIdWithAmountOfWrittenNewsDTOBuilder()
                .setAuthorId(entity.getAuthorId())
                .setAmountOfWrittenNews(
                        entity.getAmountOfWrittenNews())
                .build();
    }
}
