package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final NewsTagRepository newsTagRepository;

    @Override
    public News fromDTO(NewsDTO newsDTO) throws ServiceBadRequestParameterException {
        return News
                .builder()
                .id(newsDTO.getId())
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .author(authorRepository.findById(newsDTO.getAuthorId()).orElseThrow(() -> {
                    log.log(WARN, "Not found author by ID: " + newsDTO.getAuthorId());
                    return new ServiceBadRequestParameterException("service.exception.not_exists_author_by_id");
                }))
                .comments(commentRepository.findByNewsId(newsDTO.getId()))
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
                .countComments(news.getComments() != null ? news.getComments().size() : 0)
                .countTags(news.getTags() != null ? news.getTags().size() : 0)
                .created(news.getCreated())
                .modified(news.getModified())
                .build();
    }
}