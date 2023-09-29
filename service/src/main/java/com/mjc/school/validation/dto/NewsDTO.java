package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class NewsDTO {
    private long id;
    private String title;
    private String content;
    private long authorId;
    @JsonIgnore
    private Author author;
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
    private List<Comment> comments;
    private long countTags;
    @JsonIgnore
    private List<Tag> tags;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        if (author != null) {
            this.setAuthorId(author.getId());
        }
        this.author = author;
    }

    public long getAuthorId() {
        return authorId;
    }


    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        if (comments != null) {
            this.setCountComments(comments.size());
        }
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        if (tags != null) {
            this.setCountTags(tags.size());
        }
        this.tags = tags;
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

        public NewsDTOBuilder setAuthor(Author author) {
            this.newsDTO.setAuthor(author);
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

        public NewsDTOBuilder setComments(List<Comment> comments) {
            this.newsDTO.setComments(comments);
            return this;
        }

        public NewsDTOBuilder setTags(List<Tag> tags) {
            this.newsDTO.setTags(tags);
            return this;
        }

        public NewsDTO build() {
            return this.newsDTO;
        }
    }
}