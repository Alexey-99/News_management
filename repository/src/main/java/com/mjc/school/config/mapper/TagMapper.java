package com.mjc.school.config.mapper;

import com.mjc.school.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_NAME;

@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Tag.TagBuilder()
                .setId(resultSet.getLong(TABLE_TAGS_COLUMN_ID))
                .setName(resultSet.getString(TABLE_TAGS_COLUMN_NAME))
                .build();
    }
}