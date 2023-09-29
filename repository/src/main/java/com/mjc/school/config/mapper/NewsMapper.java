package com.mjc.school.config.mapper;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.News;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_AUTHORS_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_TITLE;

@Component
public class NewsMapper implements RowMapper<News> {
    @Override
    public News mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new News.NewsBuilder()
                .setId(resultSet.getLong(TABLE_NEWS_COLUMN_ID))
                .setTitle(resultSet.getString(TABLE_NEWS_COLUMN_TITLE))
                .setContent(resultSet.getString(TABLE_NEWS_COLUMN_CONTENT))
                .setAuthor(
                        new Author.AuthorBuilder()
                                .setId(
                                        resultSet.getLong(TABLE_NEWS_COLUMN_AUTHORS_ID))
                                .build())
                .setCreated(resultSet.getString(TABLE_NEWS_COLUMN_CREATED))
                .setModified(resultSet.getString(TABLE_NEWS_COLUMN_MODIFIED))
                .build();
    }
}