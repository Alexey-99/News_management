package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsExistsNewsById;
import com.mjc.school.validation.dto.abstraction.AbstractEntityDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;

@Validated
public class CommentDTO
        extends AbstractEntityDTO
        implements Serializable {
    @JsonIgnore
    private long id;
    @NotNull(message = BAD_COMMENT_CONTENT)
    @Size(min = 3, max = 255, message = BAD_COMMENT_CONTENT)
    private String content;
    @Min(value = 1, message = BAD_ID)
    @IsExistsNewsById(message = NO_ENTITY_WITH_COMMENT_NEWS_ID)
    private long newsId;
    private String created;
    private String modified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.newsId);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        CommentDTO otherCommentDTO = (CommentDTO) object;
        if (this.id != otherCommentDTO.id) {
            return false;
        }
        if (this.newsId != otherCommentDTO.newsId) {
            return false;
        }
        if (this.content == null) {
            if (otherCommentDTO.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherCommentDTO.content)) {
            return false;
        }
        if (this.created == null) {
            if (otherCommentDTO.created != null) {
                return false;
            }
        } else if (!this.created.equals(otherCommentDTO.created)) {
            return false;
        }
        if (this.modified == null) {
            if (otherCommentDTO.modified != null) {
                return false;
            }
        } else if (!this.modified.equals(otherCommentDTO.modified)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentDTO{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", newsId=").append(newsId);
        sb.append(", created='").append(created).append('\'');
        sb.append(", modified='").append(modified).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class CommentDTOBuilder {
        private final CommentDTO commentDTO;

        public CommentDTOBuilder() {
            this.commentDTO = new CommentDTO();
        }

        public CommentDTOBuilder setId(long id) {
            this.commentDTO.setId(id);
            return this;
        }

        public CommentDTOBuilder setContent(String content) {
            this.commentDTO.setContent(content);
            return this;
        }

        public CommentDTOBuilder setCreated(String created) {
            this.commentDTO.setCreated(created);
            return this;
        }

        public CommentDTOBuilder setModified(String modified) {
            this.commentDTO.setModified(modified);
            return this;
        }

        public CommentDTOBuilder setNewsId(long newsId) {
            this.commentDTO.setNewsId(newsId);
            return this;
        }

        public CommentDTO build() {
            return this.commentDTO;
        }
    }
}