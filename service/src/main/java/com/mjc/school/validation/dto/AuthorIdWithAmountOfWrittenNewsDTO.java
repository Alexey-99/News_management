package com.mjc.school.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
@Setter
@NoArgsConstructor
public class AuthorIdWithAmountOfWrittenNewsDTO implements Serializable {
    private long authorId;
    private long amountOfWrittenNews;
}