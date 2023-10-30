package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.model.author.Author;
import com.mjc.school.validation.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<AuthorDTO, Author> {
    @Override
    public Author fromDTO(AuthorDTO authorDTO) {
        return Author
                .builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .build();
    }

    @Override
    public AuthorDTO toDTO(Author author) {
        return AuthorDTO
                .builder()
                .id(author.getId())
                .name(author.getName())
                .countNews(author.getNews() != null
                        ? author.getNews().size()
                        : 0)
                .build();
    }
}