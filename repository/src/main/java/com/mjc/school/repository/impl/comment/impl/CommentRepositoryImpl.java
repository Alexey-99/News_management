package com.mjc.school.repository.impl.comment.impl;

import com.mjc.school.config.mapper.CommentMapper;
import com.mjc.school.entity.Comment;
import com.mjc.school.repository.impl.CRUDOperationRepositoryImpl;
import com.mjc.school.repository.impl.comment.CommentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentRepositoryImpl
        extends CRUDOperationRepositoryImpl<Comment>
        implements CommentRepository {
    private static final Logger log = LogManager.getLogger();
    private final EntityManager entityManager;

    @Autowired
    protected CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Autowired
    private CommentMapper commentsMapper;
    private static final String SELECT_COMMENT_BY_NEWS_ID = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :news_id;
            """;

    @Override
    public List<Comment> findByNewsId(long newsId, int page, int size) {
        return null;
    }

    private static final String SELECT_ALL_COMMENTS = """
            SELECT id, content, news_id, created, modified
            FROM comments;
            """;

    @Override
    public List<Comment> findAll(int page, int size) {
        return null;
    }


    private static final String QUERY_INSERT_COMMENT = """
            INSERT INTO comments (content, news_id, created, modified)
            VALUES (:content, :news_id, :created, :modified);
            """;

    @Override
    public boolean create(Comment comment) {
        return false;
    }

    private static final String QUERY_DELETE_COMMENT_BY_NEWS_ID = """
            DELETE
            FROM comments
            WHERE news_id = :news_id;
            """;

    @Override
    public boolean deleteByNewsId(long newsId) {
        return false;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}