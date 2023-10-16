package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.Comment;
import com.mjc.school.validation.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<CommentDTO, Comment> {
    @Override
    public Comment fromDTO(CommentDTO commentDTO) {
        return Comment
                .builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .newsId(commentDTO.getNewsId())
                .created(commentDTO.getCreated())
                .modified(commentDTO.getModified())
                .build();
    }

    @Override
    public CommentDTO toDTO(Comment comment) {
        return CommentDTO
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .newsId(comment.getNews().getId())
                .created(comment.getCreated())
                .modified(comment.getModified())
                .build();
    }
}