package com.mjc.school.converter;

import com.mjc.school.entity.Author;
import com.mjc.school.validation.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public Author toAuthor(AuthorDTO authorDTO) {
        return new Author
                .AuthorBuilder()
                .setId(authorDTO.getId())
                .setName(authorDTO.getName())
                .setNews(authorDTO.getNews())
                .build();
    }

    public AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO
                .AuthorDTOBuilder()
                .setId(author.getId())
                .setName(author.getName())
                .setNews(author.getNews())
                .build();
    }

}