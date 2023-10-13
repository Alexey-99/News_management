package com.mjc.school.repository.impl.comment.impl;

import com.mjc.school.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl {
    private final EntityManager entityManager;

    private static final String SELECT_COMMENT_BY_NEWS_ID = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :news_id;
            """;

    public List<Comment> findByNewsId(long newsId, int page, int size) {
        return null;
    }

    public List<Comment> findByNewsId(long newsId) {
        return null;
    }

    private static final String SELECT_ALL_COMMENTS = """
            SELECT id, content, news_id, created, modified
            FROM comments;
            """;

    public List<Comment> findAll(int page, int size) {
        return null;
    }


    private static final String QUERY_INSERT_COMMENT = """
            INSERT INTO comments (content, news_id, created, modified)
            VALUES (:content, :news_id, :created, :modified);
            """;

    public boolean create(Comment comment) {
        return false;
    }

    private static final String QUERY_DELETE_COMMENT_BY_NEWS_ID = """
            DELETE
            FROM comments
            WHERE news_id = :news_id;
            """;

    public boolean deleteByNewsId(long newsId) {
        return false;
    }

    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}