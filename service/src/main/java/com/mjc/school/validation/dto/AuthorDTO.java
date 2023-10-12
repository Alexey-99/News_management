package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsAuthorName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthorDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = BAD_AUTHOR_NAME)
    @NotBlank(message = BAD_AUTHOR_NAME)
    @Size(min = 3, max = 15, message = "40001")
//"author.name_not_valid"
    @IsNotExistsAuthorName(message = BAD_PARAMETER_AUTHOR_NAME_EXISTS)
    private String name;

    private int countNews;
}