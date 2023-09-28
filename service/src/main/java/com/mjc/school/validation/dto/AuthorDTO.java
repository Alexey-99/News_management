package com.mjc.school.validation.dto;

import com.mjc.school.entity.News;

import java.util.List;

public class AuthorDTO {
    private long id;
    private String name;
    private List<News> news;

    public AuthorDTO(long id, String name, List<News> news) {
        this.id = id;
        this.name = name;
        this.news = news;
    }

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
}