package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class TagDTO implements Serializable {
    @JsonIgnore
    private long id;

    @NotNull(message = "tag_dto.name.not_valid.null")
    @NotBlank(message = "tag_dto.name.not_valid.is_blank")
    @Size(min = 3, max = 15, message = "tag_dto.name.not_valid.size")
    private String name;

    private long countNews;
}