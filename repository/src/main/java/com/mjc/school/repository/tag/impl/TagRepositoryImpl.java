package com.mjc.school.repository.tag.impl;

import com.mjc.school.config.mapper.TagMapper;
import com.mjc.school.entity.Tag;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
            VALUES (:name);
            """;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean create(Tag tag) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_INSERT_TAG,
                    new MapSqlParameterSource()
                            .addValue(TABLE_TAGS_COLUMN_NAME, tag.getName()))
                    > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_INSERT_TAG_TO_NEWS = """
            INSERT INTO news_tags (news_id, tags_id)
            VALUES (:news_id, :tags_id);
            """;

    /**
     * Add tag to news by id.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean addToNews(long tagId, long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(
                    QUERY_INSERT_TAG_TO_NEWS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId)
                            .addValue(TABLE_NEWS_TAGS_COLUMN_TAGS_ID, tagId))
                    > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_TAG_FROM_NEWS = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id
                AND tags_id = :tags_id;
            """;

    /**
     * Remove tag from news by id.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean removeTagFromNews(long tagId, long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(
                    QUERY_DELETE_TAG_FROM_NEWS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId)
                            .addValue(TABLE_NEWS_TAGS_COLUMN_TAGS_ID, tagId)) > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_TAG_BY_ID = """
            DELETE
            FROM tags
            WHERE id = :id;
            """;

    /**
     * Delete tag by id.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteById(long tagId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_TAG_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_TAGS_COLUMN_ID, tagId))
                    > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_TAG_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE tags_id = :tags_id;
            """;

    /**
     * Delete by tag id from table tags news tag.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteByTagIdFromTableTagsNews(long tagId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_TAG_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_TAGS_COLUMN_TAGS_ID, tagId))
                    > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_UPDATE_TAG = """
            UPDATE tags
            SET name = :name
            WHERE id = :id;
            """;

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean update(Tag tag) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_UPDATE_TAG,
                    new MapSqlParameterSource()
                            .addValue(TABLE_TAGS_COLUMN_ID, tag.getId())
                            .addValue(TABLE_TAGS_COLUMN_NAME, tag.getName()))
                    > 0;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_ALL_TAGS = """
            SELECT id, name
            FROM tags;
            """;

    /**
     * Find all tags list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<Tag> findAllTags() throws RepositoryException {
        try {
            return jdbcTemplate.query(QUERY_SELECT_ALL_TAGS, tagMapper);
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_TAG_BY_ID = """
            SELECT id, name
            FROM tags
            WHERE id = :id;
            """;

    /**
     * Find by id tag.
     *
     * @param id the id
     * @return the tag
     * @throws RepositoryException the repository exception
     */
    @Override
    public Tag findById(long id) throws RepositoryException {
        try {
            List<Tag> tagListResult = jdbcTemplate
                    .query(QUERY_SELECT_TAG_BY_ID,
                            new MapSqlParameterSource()
                                    .addValue(TABLE_TAGS_COLUMN_ID, id), tagMapper);
            return !tagListResult.isEmpty() ? tagListResult.get(0) : null;
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_TAG_BY_NEWS_ID = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON tags.id = news_tags.tags_id
            WHERE news_tags.news_id = :news_id;
            """;

    /**
     * Find by news id list.
     *
     * @param newsId the news id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<Tag> findByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.query(QUERY_SELECT_TAG_BY_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId),
                    tagMapper);
        } catch (DataAccessException e) {
            throw new RepositoryException(e);
        }
    }
}