package com.mjc.school.converter;

import com.mjc.school.entity.News;
import com.mjc.school.validation.dto.NewsDTO;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter {
    public News toNews(NewsDTO newsDTO) {
        return new News
                .NewsBuilder()
                .setId(newsDTO.getId())
                .setTitle(newsDTO.getTitle())
                .setContent(newsDTO.getContent())
                .setAuthor(newsDTO.getAuthor())
                .setComments(newsDTO.getComments())
                .setTags(newsDTO.getTags())
                .setCreated(newsDTO.getCreated())
                .setModified(newsDTO.getModified())
                .build();
    }

    public NewsDTO toNewsDTO(News news) {
        return new NewsDTO
                .NewsDTOBuilder()
                .setId(news.getId())
                .setTitle(news.getTitle())
                .setContent(news.getContent())
                .setAuthor(news.getAuthor())
                .setComments(news.getComments())
                .setTags(news.getTags())
                .setCreated(news.getCreated())
                .setModified(news.getModified())
                .build();
    }
}