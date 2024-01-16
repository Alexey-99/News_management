package com.mjc.school.converter;

import com.mjc.school.model.Comment;
import com.mjc.school.validation.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
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