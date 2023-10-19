package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsExistsAuthorById;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
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
    @JsonIgnore
    private long id;

    @NotNull(message = "news_dto.title.not_valid.null")
    @NotBlank(message = "news_dto.title.not_valid.is_blank")
    @Size(min = 5, max = 30, message = "news_dto.title.not_valid.size")
    private String title;

    @NotNull(message = "news_dto.content.not_valid.null")
    @NotBlank(message = "news_dto.content.not_valid.is_blank")
    @Size(min = 5, max = 255, message = "news_dto.content.not_valid.size")
    private String content;

    @Min(value = 1, message = "news_dto.author_id.not_valid.min")
    @IsExistsAuthorById(message = "news_dto.author_id.not_valid.not_exists_author_by_id")
    private long authorId;

    private long countComments;

    private long countTags;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private String created;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private String modified;
}