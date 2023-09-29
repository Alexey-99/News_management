package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class AuthorDTO {
    private long id;
    private String name;
    private int countNews;
    @JsonIgnore
    private List<NewsDTO> news;

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

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        if (news != null) {
            this.countNews = news.size();
        }
        this.news = news;
    }

    public int getCountNews() {
        return countNews;
    }

    public void setCountNews(int countNews) {
        this.countNews = countNews;
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

        public AuthorDTOBuilder setNews(List<NewsDTO> news) {
            this.authorDTO.setNews(news);
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