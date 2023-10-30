package com.mjc.school.model.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorIdWithAmountOfWrittenNews implements Serializable {
    private long authorId;
    private long amountOfWrittenNews;
}