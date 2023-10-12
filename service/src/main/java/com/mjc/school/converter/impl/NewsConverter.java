package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.News;
import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.repository.impl.comment.CommentRepository;
import com.mjc.school.repository.impl.tag.TagRepository;
import com.mjc.school.validation.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public News fromDTO(NewsDTO newsDTO) {
        return News
                .builder()
                .id(newsDTO.getId())
                .title(newsDTO.getTitle())
                .content(newsDTO.getContent())
                .author(
                        authorRepository.findById(
                                newsDTO.getAuthorId()))
//                .setComments(
//                        commentRepository.findByNewsId(
//                                newsDTO.getId()))
//                .setTags(
//                        tagRepository.findByNewsId(
//                                newsDTO.getId()))
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