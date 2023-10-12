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
        return new News
                .NewsBuilder()
                .setId(newsDTO.getId())
                .setTitle(newsDTO.getTitle())
                .setContent(newsDTO.getContent())
                .setAuthor(
                        authorRepository.findById(
                                newsDTO.getAuthorId()))
//                .setComments(
//                        commentRepository.findByNewsId(
//                                newsDTO.getId()))
//                .setTags(
//                        tagRepository.findByNewsId(
//                                newsDTO.getId()))
                .setCreated(newsDTO.getCreated())
                .setModified(newsDTO.getModified())
                .build();
    }

    @Override
    public NewsDTO toDTO(News news) {
        return new NewsDTO
                .NewsDTOBuilder()
                .setId(news.getId())
                .setTitle(news.getTitle())
                .setContent(news.getContent())
                .setAuthorId(news.getAuthor().getId())
                .setCountComments(news.getComments() != null
                        ? news.getComments().size()
                        : 0)
                .setCountTags(news.getTags() != null
                        ? news.getTags().size()
                        : 0)
                .setCreated(news.getCreated())
                .setModified(news.getModified())
                .build();
    }
}