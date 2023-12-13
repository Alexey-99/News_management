package com.mjc.school.validation.dto;

import com.mjc.school.validation.annotation.IsExistsNewsById;
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
public class CommentDTO implements Serializable {
    private long id;

    @NotNull(message = "comment_dto.content.not_valid.null")
    @NotBlank(message = "comment_dto.content.not_valid.is_blank")
    @Size(min = 3, max = 255, message = "comment_dto.content.not_valid.size")
    private String content;

    @Min(value = 1, message = "comment_dto.news_id.not_valid.min")
    @IsExistsNewsById(message = "comment_dto.news_id.not_valid.not_exists_news_by_id")
    private long newsId;

    private String created;

    private String modified;
}