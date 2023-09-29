package com.mjc.school.config.mapper;

import com.mjc.school.entity.Author;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_NAME;

@Component
public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Author.AuthorBuilder()
                .setId(resultSet.getLong(TABLE_AUTHORS_COLUMN_ID))
                .setName(resultSet.getString(TABLE_AUTHORS_COLUMN_NAME))
                .build();
    }
}
