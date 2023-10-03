package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_AUTHOR_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_TITLE;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_NEWS_TITLE_EXISTS;

@Validated
public class NewsDTO {
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
    @JsonIgnore
    private AuthorDTO author;
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
    private long countComments;
    @JsonIgnore
    private List<CommentDTO> comments;
    private long countTags;
    @JsonIgnore
    private List<TagDTO> tags;

    public NewsDTO() {
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO authorDTO) {
        if (authorDTO != null) {
            this.authorId = authorDTO.getId();
        }
        this.author = authorDTO;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> commentsDTO) {
        if (commentsDTO != null) {
            this.setCountComments(commentsDTO.size());
        }
        this.comments = commentsDTO;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tagsDTO) {
        if (tagsDTO != null) {
            this.setCountTags(tagsDTO.size());
        }
        this.tags = tagsDTO;
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

        public NewsDTOBuilder setAuthor(AuthorDTO authorDTO) {
            this.newsDTO.setAuthor(authorDTO);
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

        public NewsDTOBuilder setComments(List<CommentDTO> commentsDTO) {
            this.newsDTO.setComments(commentsDTO);
            return this;
        }

        public NewsDTOBuilder setTags(List<TagDTO> tagsDTO) {
            this.newsDTO.setTags(tagsDTO);
            return this;
        }

        public NewsDTO build() {
            return this.newsDTO;
        }
    }
}