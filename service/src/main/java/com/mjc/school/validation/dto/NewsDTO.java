package com.mjc.school.validation.dto;

import com.mjc.school.validation.annotation.IsExistsAuthorById;
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
import java.util.List;

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
    @Min(value = 1, message = "news_dto.author_id.not_valid.min")
    @IsExistsAuthorById(message = "news_dto.author_id.not_valid.not_exists_author_by_id")
    private long authorId;

    private List<CommentDTO> comments;
    private long countComments;

    private List<TagDTO> tags;
    private long countTags;

    private String created;

    private String modified;
}