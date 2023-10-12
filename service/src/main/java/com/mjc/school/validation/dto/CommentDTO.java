package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsExistsNewsById;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Validated
public class CommentDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = BAD_COMMENT_CONTENT)
    @Size(min = 3, max = 255, message = BAD_COMMENT_CONTENT)
    private String content;

    @Min(value = 1, message = BAD_ID)
    @IsExistsNewsById(message = NO_ENTITY_WITH_COMMENT_NEWS_ID)
    private long newsId;

    private String created;

    private String modified;
}