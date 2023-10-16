package com.mjc.school;

import com.mjc.school.abstr.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorIdWithAmountOfWrittenNews extends AbstractEntity implements Serializable {
    private long authorId;
    private long amountOfWrittenNews;
}