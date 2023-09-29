package com.mjc.school.repository.comment.impl;

import com.mjc.school.config.mapper.CommentMapper;
import com.mjc.school.entity.Comment;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.comment.CommentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_NEWS_ID;
import static org.apache.logging.log4j.Level.ERROR;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    @Qualifier("namedJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private CommentMapper commentsMapper;
    private static final String SELECT_COMMENT_BY_NEWS_ID = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :news_id;
            """;

    @Override
    public List<Comment> findByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_COMMENT_BY_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, newsId),
                    commentsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }

    }

    private static final String SELECT_ALL_COMMENTS = """
            SELECT id, content, news_id, created, modified
            FROM comments;
            """;

    @Override
    public List<Comment> findAll() throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_ALL_COMMENTS,
                    commentsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }


    private static final String SELECT_COMMENT_BY_ID = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE id = :id;
            """;

    @Override
    public Comment findById(long id) throws RepositoryException {
        try {
            List<Comment> commentListResult = jdbcTemplate.query(SELECT_COMMENT_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_ID, id),
                    commentsMapper);
            return !commentListResult.isEmpty() ? commentListResult.get(0)
                    : null;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_INSERT_COMMENT = """
            INSERT INTO comments (content, news_id, created, modified)
            VALUES (:content, :news_id, :created, :modified);
            """;

    @Override
    public boolean create(Comment comment) throws RepositoryException {
        try {
            return jdbcTemplate.update(
                    QUERY_INSERT_COMMENT,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_CONTENT, comment.getContent())
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, comment.getNews().getId())
                            .addValue(TABLE_COMMENTS_COLUMN_CREATED, comment.getCreated())
                            .addValue(TABLE_COMMENTS_COLUMN_MODIFIED, comment.getModified()))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_UPDATE_COMMENT = """
            UPDATE comments
            SET content = :content,
                news_id = :news_id,
                modified = :modified
            WHERE id = :id;
            """;

    @Override
    public boolean update(Comment comment) throws RepositoryException {
        try {
            return jdbcTemplate.update(
                    QUERY_UPDATE_COMMENT,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_CONTENT, comment.getContent())
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, comment.getNews().getId())
                            .addValue(TABLE_COMMENTS_COLUMN_MODIFIED, comment.getModified())
                            .addValue(TABLE_COMMENTS_COLUMN_ID, comment.getId()))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_COMMENT_BY_ID = """
            DELETE
            FROM comments
            WHERE id = :id;
            """;

    @Override
    public boolean deleteById(long id) throws RepositoryException {
        try {
            return jdbcTemplate.update(
                    QUERY_DELETE_COMMENT_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_ID, id))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_COMMENT_BY_NEWS_ID = """
            DELETE
            FROM comments
            WHERE news_id = :news_id;
            """;

    @Override
    public boolean deleteByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_COMMENT_BY_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, newsId))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }
}