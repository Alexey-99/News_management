package com.mjc.school.converter.impl;

import com.mjc.school.converter.Converter;
import com.mjc.school.entity.Comment;
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
                .created(commentDTO.getCreated())
                .modified(commentDTO.getModified())
                .build();
    }

    @Override
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