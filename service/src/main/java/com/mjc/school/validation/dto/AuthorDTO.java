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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthorDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = "author.name.not_valid.null")
    @NotBlank(message = "author.name..not_valid.is_blank")
    @Size(min = 3, max = 15, message = "author.name.not_valid.size")
    @IsNotExistsAuthorName(message = "author.name.not_valid.already_exists")
    private String name;

    private int countNews;
}