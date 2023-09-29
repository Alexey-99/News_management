package com.mjc.school.config.mapper;

import com.mjc.school.entity.Comment;
import com.mjc.school.entity.News;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_COMMENTS_COLUMN_NEWS_ID;

@Component
public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Comment.CommentBuilder()
                .setId(resultSet.getLong(TABLE_COMMENTS_COLUMN_ID))
                .setContent(resultSet.getString(TABLE_COMMENTS_COLUMN_CONTENT))
                .setNews(new News.NewsBuilder()
                        .setId(resultSet.getLong(TABLE_COMMENTS_COLUMN_NEWS_ID))
                        .build())
                .setCreated(resultSet.getString(TABLE_COMMENTS_COLUMN_CREATED))
                .setModified(resultSet.getString(TABLE_COMMENTS_COLUMN_MODIFIED))
                .build();
    }
}
