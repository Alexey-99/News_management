package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsTagName;
import com.mjc.school.validation.dto.abstraction.AbstractEntityDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_TAF_NAME_EXISTS;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;

@Validated
public class TagDTO
        extends AbstractEntityDTO
        implements Serializable {
    @JsonIgnore
    private long id;
    @NotNull(message = BAD_TAG_NAME)
    @Size(min = 3, max = 15, message = BAD_TAG_NAME)
    @IsNotExistsTagName(message = BAD_PARAMETER_TAF_NAME_EXISTS)
    private String name;
    private long countNews;

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

    public long getCountNews() {
        return countNews;
    }

    public void setCountNews(long countNews) {
        this.countNews = countNews;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.name != null ? this.name.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.countNews);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        TagDTO otherTagDTO = (TagDTO) object;
        if (this.id != otherTagDTO.id) {
            return false;
        }
        if (this.name == null) {
            if (otherTagDTO.name != null) {
                return false;
            }
        } else if (!this.name.equals(otherTagDTO.name)) {
            return false;
        }
        if (this.countNews != otherTagDTO.countNews) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagDTO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", countNews=").append(countNews);
        sb.append('}');
        return sb.toString();
    }

    public static class TagDTOBuilder {
        private final TagDTO tagDTO;

        public TagDTOBuilder() {
            this.tagDTO = new TagDTO();
        }

        public TagDTOBuilder setId(long id) {
            this.tagDTO.setId(id);
            return this;
        }

        public TagDTOBuilder setName(String name) {
            this.tagDTO.setName(name);
            return this;
        }

        public TagDTOBuilder setCountNews(long countNews) {
            this.tagDTO.setCountNews(countNews);
            return this;
        }

        public TagDTO build() {
            return this.tagDTO;
        }
    }
}