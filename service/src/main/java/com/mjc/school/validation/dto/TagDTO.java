package com.mjc.school.validation.dto;

import com.mjc.school.entity.News;

import java.util.List;

public class TagDTO {
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

        public TagDTOBuilder setNews(List<News> news) {
            this.tagDTO.setNews(news);
            return this;
        }

        public TagDTO build() {
            return this.tagDTO;
        }
    }
}