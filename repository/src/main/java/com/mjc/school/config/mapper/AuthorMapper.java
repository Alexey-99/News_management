package com.mjc.school.config.mapper;

import com.mjc.school.entity.Author;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_NAME;

/**
 * The type Author mapper.
 */
@Component
public class AuthorMapper implements RowMapper<Author> {
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
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Author.AuthorBuilder()
                .setId(resultSet.getLong(TABLE_AUTHORS_COLUMN_ID))
                .setName(resultSet.getString(TABLE_AUTHORS_COLUMN_NAME))
                .build();
    }
}
