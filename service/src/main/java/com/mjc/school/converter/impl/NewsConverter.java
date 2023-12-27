package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.TagDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final NewsTagRepository newsTagRepository;
    private final TagConverter tagConverter;
    private final CommentConverter commentConverter;
    private final AuthorConverter authorConverter;

    @Override
    public News fromDTO(NewsDTO newsDTO) throws ServiceBadRequestParameterException {
        return News
                .builder()
                .id(newsDTO.getId())
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .author(authorRepository.findByName(newsDTO.getAuthorName()).orElseThrow(() -> {
                    log.log(WARN, "Not found author by name: " + newsDTO.getAuthorName());
                    return new ServiceBadRequestParameterException("service.exception.not_exists_author_by_name");
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
                .author(authorConverter.toDTO(news.getAuthor()))
                .comments(news.getComments() != null ?
                        news.getComments()
                                .stream()
                                .map(commentConverter::toDTO)
                                .toList() :
                        List.of())
                .tags(news.getTags() != null ?
                        news.getTags()
                                .stream()
                                .map(newsTag -> tagConverter.toDTO(newsTag.getTag()))
                                .toList() :
                        List.of())
                .created(news.getCreated())
                .modified(news.getModified())
                .build();
    }
}