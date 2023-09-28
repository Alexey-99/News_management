package com.mjc.school.validation.dto;

import com.mjc.school.entity.News;

import java.util.List;

public class AuthorDTO {
    private long id;
    private String name;
    private List<News> news;

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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
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

        public AuthorDTOBuilder setNews(List<News> news) {
            this.authorDTO.setNews(news);
            return this;
        }

        public AuthorDTO build() {
            return this.authorDTO;
        }
    }
}