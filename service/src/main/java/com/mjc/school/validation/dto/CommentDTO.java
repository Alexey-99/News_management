package com.mjc.school.validation.dto;

import com.mjc.school.entity.News;

public class CommentDTO {
    private long id;
    private String content;
    private News news;
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

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
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

        public CommentDTOBuilder setNews(News news) {
            this.commentDTO.setNews(news);
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

        public CommentDTO build() {
            return this.commentDTO;
        }
    }
}