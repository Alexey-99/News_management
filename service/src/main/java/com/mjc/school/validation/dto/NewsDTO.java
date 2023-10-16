package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_AUTHOR_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_CONTENT;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Validated
public class NewsDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = "news_dto.title.not_valid.null")
    @Size(min = 5, max = 30, message = "news_dto.title.not_valid.size")
    @IsNotExistsNewsTitle(message = "news_dto.title.not_valid.exists_news_title")
    private String title;

    @NotNull(message = BAD_NEWS_CONTENT)
    @Size(min = 5, max = 255, message = BAD_NEWS_CONTENT)
    private String content;

    @Min(value = 1, message = BAD_NEWS_AUTHOR_ID)
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