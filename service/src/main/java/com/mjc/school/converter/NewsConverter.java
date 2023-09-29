package com.mjc.school.converter;

import com.mjc.school.entity.News;
import com.mjc.school.validation.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    @Autowired
    private AuthorConverter authorConverter;
    @Autowired
    private CommentConverter commentConverter;
    @Autowired
    private TagConverter tagConverter;

    public News fromDTO(NewsDTO newsDTO) {
        return new News
                .NewsBuilder()
                .setId(newsDTO.getId())
                .setTitle(newsDTO.getTitle())
                .setContent(newsDTO.getContent())
                .setAuthor(
                        authorConverter.fromDTO(
                                newsDTO.getAuthor()))
                .setComments(
                        newsDTO.getComments()
                                .stream()
                                .map(commentDTO ->
                                        commentConverter.fromDTO(commentDTO))
                                .toList())
                .setTags(
                        newsDTO.getTags()
                                .stream()
                                .map(tagDTO -> tagConverter.fromDTO(tagDTO))
                                .toList())
                .setCreated(newsDTO.getCreated())
                .setModified(newsDTO.getModified())
                .build();
    }

    public NewsDTO toDTO(News news) {
        return new NewsDTO
                .NewsDTOBuilder()
                .setId(news.getId())
                .setTitle(news.getTitle())
                .setContent(news.getContent())
                .setAuthor(
                        authorConverter.toDTO(
                                news.getAuthor()))
                .setComments(
                        news.getComments()
                                .stream()
                                .map(comment ->
                                        commentConverter.toDTO(comment))
                                .toList())
                .setTags(
                        news.getTags()
                                .stream()
                                .map(tag -> tagConverter.toDTO(tag))
                                .toList())
                .setCreated(news.getCreated())
                .setModified(news.getModified())
                .build();
    }
}