package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import com.mjc.school.validation.dto.abstraction.AbstractEntityDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_AUTHOR_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_TITLE;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_NEWS_TITLE_EXISTS;

@Validated
public class NewsDTO
        extends AbstractEntityDTO
        implements Serializable {
    @JsonIgnore
    private long id;
    @NotNull(message = BAD_NEWS_TITLE)
    @Size(min = 5, max = 30, message = BAD_NEWS_TITLE)
    @IsNotExistsNewsTitle(message = BAD_PARAMETER_NEWS_TITLE_EXISTS)
    private String title;
    @NotNull(message = BAD_NEWS_CONTENT)
    @Size(min = 5, max = 255, message = BAD_NEWS_CONTENT)
    private String content;
    @Min(value = 1, message = BAD_NEWS_AUTHOR_ID)
    private long authorId;
    private long countComments;
    private long countTags;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private String created;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private String modified;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getCountComments() {
        return countComments;
    }

    public void setCountComments(long countComments) {
        this.countComments = countComments;
    }

    public long getCountTags() {
        return countTags;
    }

    public void setCountTags(long countTags) {
        this.countTags = countTags;
    }

    public String getCreated() {
        return created;
    }

    @JsonIgnore
    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    @JsonIgnore
    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.title != null ? this.title.hashCode() : 1);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.authorId);
        result = result * PRIME + Long.hashCode(this.countComments);
        result = result * PRIME + Long.hashCode(this.countTags);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        NewsDTO otherNewsDTO = (NewsDTO) object;
        if (this.id != otherNewsDTO.id) {
            return false;
        }
        if (this.title == null) {
            if (otherNewsDTO.title != null) {
                return false;
            }
        } else if (!this.title.equals(otherNewsDTO.title)) {
            return false;
        }
        if (this.content == null) {
            if (otherNewsDTO.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherNewsDTO.content)) {
            return false;
        }
        if (this.authorId != otherNewsDTO.authorId) {
            return false;
        }
        if (this.countComments != otherNewsDTO.countComments) {
            return false;
        }
        if (this.countTags != otherNewsDTO.countTags) {
            return false;
        }
        if (this.created == null) {
            if (otherNewsDTO.created != null) {
                return false;
            }
        } else if (!this.created.equals(otherNewsDTO.created)) {
            return false;
        }
        if (this.modified == null) {
            if (otherNewsDTO.modified != null) {
                return false;
            }
        } else if (!this.modified.equals(otherNewsDTO.modified)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NewsDTO{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", authorId=").append(authorId);
        sb.append(", countComments=").append(countComments);
        sb.append(", countTags=").append(countTags);
        sb.append(", created='").append(created).append('\'');
        sb.append(", modified='").append(modified).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class NewsDTOBuilder {
        private final NewsDTO newsDTO;

        public NewsDTOBuilder() {
            this.newsDTO = new NewsDTO();
        }

        public NewsDTOBuilder setId(long id) {
            this.newsDTO.setId(id);
            return this;
        }

        public NewsDTOBuilder setTitle(String title) {
            this.newsDTO.setTitle(title);
            return this;
        }

        public NewsDTOBuilder setContent(String content) {
            this.newsDTO.setContent(content);
            return this;
        }

        public NewsDTOBuilder setCreated(String created) {
            this.newsDTO.setCreated(created);
            return this;
        }

        public NewsDTOBuilder setModified(String modified) {
            this.newsDTO.setModified(modified);
            return this;
        }

        public NewsDTOBuilder setCountComments(long countComments) {
            this.newsDTO.setCountComments(countComments);
            return this;
        }

        public NewsDTOBuilder setCountTags(long countTags) {
            this.newsDTO.setCountTags(countTags);
            return this;
        }

        public NewsDTOBuilder setAuthorId(long authorId) {
            this.newsDTO.setAuthorId(authorId);
            return this;
        }

        public NewsDTO build() {
            return this.newsDTO;
        }
    }
}