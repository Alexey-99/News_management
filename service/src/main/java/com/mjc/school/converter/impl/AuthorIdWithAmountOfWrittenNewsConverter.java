package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorIdWithAmountOfWrittenNewsConverter
        implements Converter<
        AuthorIdWithAmountOfWrittenNewsDTO,
        AuthorIdWithAmountOfWrittenNews> {

    @Override
    public AuthorIdWithAmountOfWrittenNews fromDTO(
            AuthorIdWithAmountOfWrittenNewsDTO entityDTO) {
        return AuthorIdWithAmountOfWrittenNews
                .builder()
                .authorId(entityDTO.getAuthorId())
                .amountOfWrittenNews(entityDTO.getAmountOfWrittenNews())
                .build();
    }

    @Override
    public AuthorIdWithAmountOfWrittenNewsDTO toDTO(
            AuthorIdWithAmountOfWrittenNews entity) {
        return AuthorIdWithAmountOfWrittenNewsDTO
                .builder()
                .authorId(entity.getAuthorId())
                .amountOfWrittenNews(entity.getAmountOfWrittenNews())
                .build();
    }
}
