package com.mjc.school.converter;

import com.mjc.school.entity.Author;
import com.mjc.school.validation.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<AuthorDTO, Author> {
    public Author fromDTO(AuthorDTO authorDTO) {
        return new Author
                .AuthorBuilder()
                .setId(authorDTO.getId())
                .setName(authorDTO.getName())
                .build();
    }

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO
                .AuthorDTOBuilder()
                .setId(author.getId())
                .setName(author.getName())
                .setCountNews(author.getNews().size())
                .build();
    }
}