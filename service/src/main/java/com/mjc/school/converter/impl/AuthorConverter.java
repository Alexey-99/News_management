package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.Author;
import com.mjc.school.validation.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<AuthorDTO, Author> {
    @Override
    public Author fromDTO(AuthorDTO authorDTO) {
        return new Author
                .AuthorBuilder()
                .setId(authorDTO.getId())
                .setName(authorDTO.getName())
                .build();
    }

    @Override
    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO
                .AuthorDTOBuilder()
                .setId(author.getId())
                .setName(author.getName())
                .setCountNews(author.getNews() != null
                        ? author.getNews().size()
                        : 0)
                .build();
    }
}