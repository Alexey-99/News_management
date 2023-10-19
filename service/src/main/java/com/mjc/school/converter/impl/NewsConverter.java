package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final NewsTagRepository newsTagRepository;

    @Override
    public News fromDTO(NewsDTO newsDTO) {
        return News
                .builder()
                .id(newsDTO.getId())
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .author(authorRepository.findById(
                                newsDTO.getAuthorId())
                        .orElse(null))
                .comments(commentRepository.findByNewsId(
                        newsDTO.getId()))
                .tags(newsTagRepository.findByNewsId(newsDTO.getId()))
                .created(newsDTO.getCreated())
                .modified(newsDTO.getModified())
                .build();
    }

    @Override
    public NewsDTO toDTO(News news) {
        return NewsDTO
                .builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .authorId(news.getAuthor().getId())
                .countComments(news.getComments() != null
                        ? news.getComments().size()
                        : 0)
                .countTags(news.getTags() != null
                        ? news.getTags().size()
                        : 0)
                .created(news.getCreated())
                .modified(news.getModified())
                .build();
    }


}