package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.Comment;
import com.mjc.school.validation.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<CommentDTO, Comment> {

    public Comment fromDTO(CommentDTO commentDTO) {
        return new Comment
                .CommentBuilder()
                .setId(commentDTO.getId())
                .setContent(commentDTO.getContent())
                .setCreated(commentDTO.getCreated())
                .setModified(commentDTO.getModified())
                .build();
    }

    public CommentDTO toDTO(Comment comment) {
        return new CommentDTO
                .CommentDTOBuilder()
                .setId(comment.getId())
                .setContent(comment.getContent())
                .setNewsId(comment.getNews().getId())
                .setCreated(comment.getCreated())
                .setModified(comment.getModified())
                .build();
    }
}