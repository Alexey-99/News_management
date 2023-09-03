package com.mjc.school.repository.tag.impl;

import com.mjc.school.config.mapper.TagMapper;
import com.mjc.school.entity.Tag;
import com.mjc.school.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_NEWS_TAGS_COLUMN_NEWS_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_TAGS_COLUMN_TAGS_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_NAME;

/**
 * The type Tag repository.
 */
@Repository
public class TagRepositoryImpl implements TagRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private TagMapper tagMapper;

    private static final String QUERY_INSERT_TAG = """
            INSERT INTO tags (name)
            VALUE(:name);
            """;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    @Override
    public boolean create(Tag tag) {
        return jdbcTemplate.update(QUERY_INSERT_TAG,
                new MapSqlParameterSource()
                        .addValue(TABLE_TAGS_COLUMN_NAME, tag.getName()))
                > 0;
    }

    private static final String QUERY_DELETE_TAG_BY_ID = """
            DELETE
            FROM tags
            WHERE id = :id;
            """;

    /**
     * Delete by id boolean.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    @Override
    public boolean deleteById(long tagId) {
        return jdbcTemplate.update(QUERY_DELETE_TAG_BY_ID,
                new MapSqlParameterSource()
                        .addValue(TABLE_TAGS_COLUMN_ID, tagId))
                > 0;
    }

    private static final String QUERY_DELETE_TAG_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE tags_id = :tags_id;
            """;

    /**
     * Delete tags by tag id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    @Override
    public boolean deleteByTagIdFromTableTagsNews(long tagId) {
        return jdbcTemplate.update(QUERY_DELETE_TAG_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_TAGS_COLUMN_TAGS_ID, tagId))
                > 0;
    }

    private static final String QUERY_UPDATE_TAG = """
            UPDATE INTO tags
            SET name = :name
            WHERE id = :id;
            """;

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     */
    @Override
    public boolean update(Tag tag) {
        return jdbcTemplate.update(QUERY_UPDATE_TAG,
                new MapSqlParameterSource()
                        .addValue(TABLE_TAGS_COLUMN_ID, tag.getId())
                        .addValue(TABLE_TAGS_COLUMN_NAME, tag.getName()))
                > 0;
    }

    private static final String QUERY_SELECT_ALL_TAGS = """
            SELECT id, name
            FROM tags;
            """;

    /**
     * Find all tags list.
     *
     * @return the list
     */
    @Override
    public List<Tag> findAllTags() {
        return jdbcTemplate.query(QUERY_SELECT_ALL_TAGS, tagMapper);
    }

    private static final String QUERY_SELECT_TAG_BY_ID = """
            SELECT id, name
            FROM tags
            WHERE id = :id;
            """;

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the tag
     */
    @Override
    public Tag findById(long id) {
        List<Tag> tagListResult = jdbcTemplate
                .query(QUERY_SELECT_TAG_BY_ID,
                        new MapSqlParameterSource()
                                .addValue(TABLE_TAGS_COLUMN_ID, id), tagMapper);
        return !tagListResult.isEmpty() ? tagListResult.get(0) : null;
    }

    private static final String QUERY_SELECT_TAG_BY_NEWS_ID = """
            SELECT tags.id, tags.name
            FROM tags INNER JOIN news_tags
            ON tags.id = news_tags.tags_id
            WHERE news_tags.news_id = :news_id;
            """;

    /**
     * Find tags by news id list.
     *
     * @param newsId the news id
     * @return the list
     */
    @Override
    public List<Tag> findByNewsId(long newsId) {
        return jdbcTemplate.query(QUERY_SELECT_TAG_BY_NEWS_ID,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId),
                tagMapper);
    }
}