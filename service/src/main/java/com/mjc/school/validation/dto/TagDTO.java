package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class TagDTO {
    private long id;
    private String name;
    private long countNews;
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

    public void setNews(List<NewsDTO> newsDTO) {
        if (newsDTO != null) {
            this.countNews = newsDTO.size();
        }
        this.news = newsDTO;
    }

    public long getCountNews() {
        return countNews;
    }

    public void setCountNews(long countNews) {
        this.countNews = countNews;
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

        public TagDTOBuilder setNews(List<NewsDTO> newsDTO) {
            this.tagDTO.setNews(newsDTO);
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