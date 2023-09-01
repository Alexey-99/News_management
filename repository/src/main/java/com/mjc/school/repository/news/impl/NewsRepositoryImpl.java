package com.mjc.school.repository.news.impl;

import com.mjc.school.config.mapper.NewsMapper;
import com.mjc.school.entity.News;
import com.mjc.school.repository.news.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_AUTHORS_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_TITLE;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_TAGS_COLUMN_NEWS_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_NAME;

/**
 * The type News repository.
 */
@Repository
public class NewsRepositoryImpl implements NewsRepository {
    @Autowired
    @Qualifier("namedJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE_NEWS = """
            INSERT INTO news (title, content, authors_id, created, modified)
            VALUE (:title, :content, :authors_id, :created, :modified);
            """;


    @Override
    public boolean create(News news) {
        return jdbcTemplate.update(SQL_CREATE_NEWS,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_COLUMN_TITLE, news.getTitle())
                        .addValue(TABLE_NEWS_COLUMN_CONTENT, news.getContent())
                        .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, news.getAuthorId())
                        .addValue(TABLE_NEWS_COLUMN_CREATED, news.getCreated())
                        .addValue(TABLE_NEWS_COLUMN_MODIFIED, news.getModified())) > 0;
    }

    private static final String SQL_DELETE_NEWS = """
            DELETE FROM news
            WHERE news.id = :news.id;
            """;


    /**
     * Delete news by id.
     *
     * @param newsId the news id
     * @return the boolean
     */
    @Override
    public boolean deleteById(long newsId) {
        return jdbcTemplate.update(SQL_DELETE_NEWS,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_COLUMN_ID, newsId)) > 0;
    }

    private static final String SQL_DELETE_NEWS_BY_AUTHOR_ID = """
            DELETE FROM news
            WHERE author_id = :author_id;
            """;

    /**
     * Delete news by author id.
     *
     * @param authorId the author id
     * @return the boolean
     */
    @Override
    public boolean deleteByAuthorId(long authorId) {
        return jdbcTemplate.update(SQL_DELETE_NEWS_BY_AUTHOR_ID,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, authorId)) > 0;
    }


    private static final String QUERY_DELETE_NEWS_ID_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id;
            """;


    /**
     * Delete news by news id from table news tags.
     *
     * @param newsId the news id
     * @return the boolean
     */
    @Override
    public boolean deleteByNewsIdFromTableNewsTags(long newsId) {
        return jdbcTemplate.update(QUERY_DELETE_NEWS_ID_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId))
                > 0;
    }

    private static final String SQL_UPDATE_NEWS = """
            UPDATE news
            SET title = :title,
                news.content = :content,
                news.authors_id = :authors_id,
                news.created = :created,
                news.modified = :modified
            WHERE news.id = :news.id;
            """;


    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     */
    @Override
    public boolean update(News news) {
        return jdbcTemplate.update(SQL_UPDATE_NEWS,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_COLUMN_ID, news.getId())
                        .addValue(TABLE_NEWS_COLUMN_TITLE, news.getTitle())
                        .addValue(TABLE_NEWS_COLUMN_CONTENT, news.getContent())
                        .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, news.getAuthorId())
                        .addValue(TABLE_NEWS_COLUMN_CREATED, news.getCreated())
                        .addValue(TABLE_NEWS_COLUMN_MODIFIED, news.getModified())) > 0;
    }

    @Autowired
    private NewsMapper newsMapper;
    private static final String SELECT_ALL_NEWS = """
            SELECT id, title, content, authors_id, modified
            FROM news;
            """;


    /**
     * Find all news list.
     *
     * @return the list
     */
    @Override
    public List<News> findAllNews() {
        return jdbcTemplate.query(SELECT_ALL_NEWS, newsMapper);
    }

    private static final String SELECT_FIND_NEWS_BY_ID = """
            SELECT id, title, content, authors_id, modified
            FROM news
            WHERE id = :id;
            """;

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     */
    @Override
    public News findNewsById(long newsId) {
        List<News> resultQuery = jdbcTemplate
                .query(SELECT_FIND_NEWS_BY_ID,
                        new MapSqlParameterSource()
                                .addValue(TABLE_NEWS_COLUMN_ID, newsId),
                        newsMapper);
        return !resultQuery.isEmpty() ? resultQuery.get(0) : null;
    }

    private static final String SELECT_FIND_NEWS_BY_TAG_NAME = """
            SELECT news.id, news.title, news.content, news.authors_id, news.modified
            FROM news INNER JOIN news_tags INNER JOIN tags
            ON news.id = news_tags.news_id AND news_tags.tags_id = tags.id
            WHERE news_tags.id = news_tags.tags_id AND tags.name = :tags.name;
            """;


    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     */
    @Override
    public List<News> findNewsByTagName(String tagName) {
        return jdbcTemplate.query(SELECT_FIND_NEWS_BY_TAG_NAME,
                new MapSqlParameterSource()
                        .addValue(TABLE_TAGS_COLUMN_NAME, tagName),
                newsMapper);
    }

    private static final String SELECT_FIND_NEWS_BY_TAG_ID = """
            SELECT news.id, news.title, news.content, news.authors_id, news.modified
            FROM news INNER JOIN news_tags INNER JOIN tags
            ON news.id = news_tags.news_id AND news_tags.tags_id = tags.id
            WHERE news_tags.id = news_tags.tags_id AND tags.id = :tags.id;
            """;


    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     */
    @Override
    public List<News> findNewsByTagId(long tagId) {
        return jdbcTemplate.query(SELECT_FIND_NEWS_BY_TAG_ID,
                new MapSqlParameterSource()
                        .addValue(TABLE_TAGS_COLUMN_ID, tagId),
                newsMapper);
    }

    private static final String SELECT_FIND_NEWS_BY_AUTHOR_ID = """
            SELECT id, title, content, authors_id, modified
            FROM news
            WHERE authors_id = :authors_id;
            """;

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     */
    @Override
    public List<News> findNewsByAuthorName(String authorName) {
        return jdbcTemplate.query(SELECT_FIND_NEWS_BY_AUTHOR_ID,
                new MapSqlParameterSource()
                        .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, authorName),
                newsMapper);
    }
}