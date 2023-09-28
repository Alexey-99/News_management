package com.mjc.school.converter;

import com.mjc.school.entity.Comment;
import com.mjc.school.validation.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public Comment toComment(CommentDTO commentDTO) {
        return new Comment
                .CommentBuilder()
                .setId(commentDTO.getId())
                .setContent(commentDTO.getContent())
                .setNews(commentDTO.getNews())
                .setCreated(commentDTO.getCreated())
                .setModified(commentDTO.getModified())
                .build();
    }

    public CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO
                .CommentDTOBuilder()
                .setId(comment.getId())
                .setContent(comment.getContent())
                .setNews(comment.getNews())
                .setCreated(comment.getCreated())
                .setModified(comment.getModified())
                .build();
    }
}