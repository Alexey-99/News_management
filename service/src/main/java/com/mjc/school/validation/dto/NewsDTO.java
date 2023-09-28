package com.mjc.school.validation.dto;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.Comment;
import com.mjc.school.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class NewsDTO {
    private long id;
    private String title;
    private String content;
    private Author author;
    private String created;
    private String modified;
    private List<Comment> comments;
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
        this.author = author;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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