package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsTagName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_TAF_NAME_EXISTS;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class TagDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = BAD_TAG_NAME)
    @Size(min = 3, max = 15, message = BAD_TAG_NAME)
    @IsNotExistsTagName(message = BAD_PARAMETER_TAF_NAME_EXISTS)
    private String name;

    private long countNews;
}