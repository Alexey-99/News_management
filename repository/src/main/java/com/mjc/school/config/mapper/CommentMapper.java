package com.mjc.school.config.mapper;

import com.mjc.school.entity.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_NEWS_ID;

/**
 * The type Comment mapper.
 */
@Component
public class CommentMapper implements RowMapper<Comment> {
    /**
     * Implementations must implement this method to map each row of data in the
     * {@code ResultSet}. This method should not call {@code next()} on the
     * {@code ResultSet}; it is only supposed to map values of the current row.
     *
     * @param resultSet the {@code ResultSet} to map (pre-initialized for the current row)
     * @param rowNum    the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered while getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Comment.CommentBuilder()
                .setId(resultSet.getLong(TABLE_COMMENTS_COLUMN_ID))
                .setContent(resultSet.getString(TABLE_COMMENTS_COLUMN_CONTENT))
                .setNewsId(resultSet.getLong(TABLE_COMMENTS_COLUMN_NEWS_ID))
                .setCreated(LocalDateTime.of(
                        resultSet.getDate(TABLE_COMMENTS_COLUMN_CREATED).toLocalDate(),
                        resultSet.getTime(TABLE_COMMENTS_COLUMN_CREATED).toLocalTime()))
                .setModified(LocalDateTime.of(
                        resultSet.getDate(TABLE_COMMENTS_COLUMN_MODIFIED).toLocalDate(),
                        resultSet.getTime(TABLE_COMMENTS_COLUMN_MODIFIED).toLocalTime()))
                .build();
    }
}
