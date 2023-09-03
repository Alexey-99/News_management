package com.mjc.school.repository.comment.impl;

import com.mjc.school.config.mapper.CommentMapper;
import com.mjc.school.entity.Comment;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_NEWS_ID;

/**
 * The type Comment repository.
 */
@Repository
public class CommentRepositoryImpl implements CommentRepository {
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

    /**
     * Find comments by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<Comment> findCommentsByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_COMMENT_BY_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, newsId),
                    commentsMapper);
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }

    }

    private static final String SELECT_ALL_COMMENTS = """
            SELECT id, content, news_id, created, modified
            FROM comments;
            """;

    /**
     * Find all comments list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<Comment> findAllComments() throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_ALL_COMMENTS,
                    commentsMapper);
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }


    private static final String SELECT_COMMENT_BY_ID = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE id = :id;
            """;

    /**
     * Find comment by id comment.
     *
     * @param id the id
     * @return the comment
     * @throws RepositoryException the repository exception
     */
    @Override
    public Comment findCommentById(long id) throws RepositoryException {
        try {
            List<Comment> commentListResult = jdbcTemplate.query(SELECT_COMMENT_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_ID, id),
                    commentsMapper);
            return !commentListResult.isEmpty() ? commentListResult.get(0) : null;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_COMMENT_NEWS_ID = """
            DELETE FROM comments
            WHERE news_id = :news_id;
            """;


    /**
     * Delete by news id comment.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_COMMENT_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_COMMENTS_COLUMN_NEWS_ID, newsId)) > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }
}