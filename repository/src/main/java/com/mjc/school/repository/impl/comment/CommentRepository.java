package com.mjc.school.repository.impl.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.repository.BaseRepository;

import java.util.List;

public interface CommentRepository
        extends BaseRepository<Comment, Long> {

    List<Comment> findByNewsId(long newsId, int page, int size);

    List<Comment> findByNewsId(long newsId);

    boolean deleteByNewsId(long newsId);
}