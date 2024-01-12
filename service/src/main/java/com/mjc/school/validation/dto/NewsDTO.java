package com.mjc.school.validation.dto;

import com.mjc.school.validation.annotation.IsExistsAuthorByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Validated
public class NewsDTO implements Serializable {
    private long id;

    @NotNull(message = "news_dto.title.not_valid.null")
    @NotBlank(message = "news_dto.title.not_valid.is_blank")
    @Size(min = 5, max = 30, message = "news_dto.title.not_valid.size")
    private String title;

    @NotNull(message = "news_dto.content.not_valid.null")
    @NotBlank(message = "news_dto.content.not_valid.is_blank")
    @Size(min = 5, max = 255, message = "news_dto.content.not_valid.size")
    private String content;

    private AuthorDTO author;

    @NotNull(message = "author_dto.name.not_valid.null")
    @NotBlank(message = "author_dto.name.not_valid.is_blank")
    @Size(min = 3, max = 15, message = "author_dto.name.not_valid.size")
    @IsExistsAuthorByName(message = "news_dto.author_name.not_valid.not_exists_author_by_name")
    private String authorName;

    private long countComments;

    private long countTags;

    private String created;

    private String modified;
}