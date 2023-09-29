package com.mjc.school.config.mapper;

import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.COUNT_ROWS;

@Component
public class AuthorIdWithAmountOfWrittenNewsMapper implements RowMapper<AuthorIdWithAmountOfWrittenNews> {
    @Override
    public AuthorIdWithAmountOfWrittenNews mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new AuthorIdWithAmountOfWrittenNews
                .AuthorIdWithAmountOfWrittenNewsBuilder()
                .setAuthorId(resultSet.getLong(TABLE_AUTHORS_COLUMN_ID))
                .setAmountOfWrittenNews(resultSet.getLong(COUNT_ROWS))
                .build();
    }
}