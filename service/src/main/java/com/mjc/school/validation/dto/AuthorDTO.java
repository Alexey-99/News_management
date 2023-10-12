package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsAuthorName;
import com.mjc.school.validation.dto.abstraction.AbstractEntityDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;

@Validated
public class AuthorDTO
        extends AbstractEntityDTO
        implements Serializable {
    @JsonIgnore
    private long id;
    @NotNull(message = BAD_AUTHOR_NAME)
    @NotBlank(message = BAD_AUTHOR_NAME)
    @Size(min = 3,
            max = 15,
            message = "40001")//"author.name_not_valid"
    @IsNotExistsAuthorName(message = BAD_PARAMETER_AUTHOR_NAME_EXISTS)
    private String name;
    private int countNews;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountNews() {
        return countNews;
    }

    public void setCountNews(int countNews) {
        this.countNews = countNews;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + Long.hashCode(this.countNews);
        result = result * PRIME + (this.name != null ? this.name.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        AuthorDTO otherAuthorDTO = (AuthorDTO) object;
        if (this.id != otherAuthorDTO.id) {
            return false;
        }
        if (this.countNews != otherAuthorDTO.countNews) {
            return false;
        }
        if (this.name == null) {
            if (otherAuthorDTO.name != null) {
                return false;
            }
        } else if (!this.name.equals(otherAuthorDTO.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthorDTO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", countNews=").append(countNews);
        sb.append('}');
        return sb.toString();
    }

    public static class AuthorDTOBuilder {
        private final AuthorDTO authorDTO;

        public AuthorDTOBuilder() {
            this.authorDTO = new AuthorDTO();
        }

        public AuthorDTOBuilder setId(long id) {
            this.authorDTO.setId(id);
            return this;
        }

        public AuthorDTOBuilder setName(String name) {
            this.authorDTO.setName(name);
            return this;
        }

        public AuthorDTOBuilder setCountNews(int countNews) {
            this.authorDTO.setCountNews(countNews);
            return this;
        }

        public AuthorDTO build() {
            return this.authorDTO;
        }
    }
}