package com.mjc.school.validation.dto;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthorDTO implements Serializable {
    private long id;

    @NotNull(message = "author_dto.name.not_valid.null")
    @NotBlank(message = "author_dto.name.not_valid.is_blank")
    @Size(min = 3, max = 15, message = "author_dto.name.not_valid.size")
    private String name;

    private int countNews;
}