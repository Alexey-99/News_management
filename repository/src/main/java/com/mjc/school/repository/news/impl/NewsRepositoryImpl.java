package com.mjc.school.repository.news.impl;

import com.mjc.school.config.mapper.NewsMapper;
import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.news.NewsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_NAME;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_AUTHORS_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CONTENT;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_CREATED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_MODIFIED;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_TITLE;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_TAGS_COLUMN_NEWS_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_TAGS_COLUMN_NAME;
import static org.apache.logging.log4j.Level.ERROR;

/**
 * The type News repository.
 */
@Repository
public class NewsRepositoryImpl implements NewsRepository {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    @Qualifier("namedJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private NewsMapper newsMapper;

    private static final String QUERY_CREATE_NEWS = """
            INSERT INTO news (title, content, authors_id, created, modified)
            VALUES (:title, :content, :authors_id, :created, :modified);
            """;

    /**
     * Create news.
     *
     * @param news the news
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean create(News news) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_CREATE_NEWS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_TITLE, news.getTitle())
                            .addValue(TABLE_NEWS_COLUMN_CONTENT, news.getContent())
                            .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, news.getAuthorId())
                            .addValue(TABLE_NEWS_COLUMN_CREATED, news.getCreated())
                            .addValue(TABLE_NEWS_COLUMN_MODIFIED, news.getModified()))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_NEWS = """
            DELETE FROM news
            WHERE id = :id;
            """;

    /**
     * Delete by id news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteById(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_NEWS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_ID, newsId))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_NEWS_BY_AUTHOR_ID = """
            DELETE FROM news
            WHERE authors_id = :authors_id;
            """;

    /**
     * Delete by author id news.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteByAuthorId(long authorId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_NEWS_BY_AUTHOR_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, authorId))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_NEWS_ID_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id;
            """;


    /**
     * Delete by news id from table news tags news.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long newsId) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_NEWS_ID_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_TAGS_COLUMN_NEWS_ID, newsId))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_UPDATE_NEWS = """
            UPDATE news
            SET title = :title,
                content = :content,
                authors_id = :authors_id,
                modified = :modified
            WHERE id = :id;
            """;

    /**
     * Update news.
     *
     * @param news the news
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean update(News news) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_UPDATE_NEWS,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_ID, news.getId())
                            .addValue(TABLE_NEWS_COLUMN_TITLE, news.getTitle())
                            .addValue(TABLE_NEWS_COLUMN_CONTENT, news.getContent())
                            .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, news.getAuthorId())
                            .addValue(TABLE_NEWS_COLUMN_MODIFIED, news.getModified()))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_ALL_NEWS = """
            SELECT id, title, content, authors_id, created, modified
            FROM news;
            """;

    /**
     * Find all news list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<News> findAll() throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_ALL_NEWS, newsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_ID = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE id = :id;
            """;

    /**
     * Find news by id news.
     *
     * @param newsId the news id
     * @return the news
     * @throws RepositoryException the repository exception
     */
    @Override
    public News findById(long newsId) throws RepositoryException {
        try {
            List<News> resultQuery = jdbcTemplate.query(SELECT_FIND_NEWS_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_ID, newsId),
                    newsMapper);
            return !resultQuery.isEmpty() ? resultQuery.get(0) : null;
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_TAG_NAME = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE
                news_tags.tags_id = tags.id
                AND tags.name = :name;
            """;


    /**
     * Find news by tag name list.
     *
     * @param tagName the tag name
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<News> findByTagName(String tagName) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_FIND_NEWS_BY_TAG_NAME,
                    new MapSqlParameterSource()
                            .addValue(TABLE_TAGS_COLUMN_NAME, tagName),
                    newsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_TAG_ID = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
             FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
             WHERE
                 news_tags.tags_id = tags.id
                 AND tags.id = :id;
             """;


    /**
     * Find news by tag id list.
     *
     * @param tagId the tag id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<News> findByTagId(long tagId) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_FIND_NEWS_BY_TAG_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_TAGS_COLUMN_ID, tagId),
                    newsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_AUTHORS_NAME = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name = :name;
            """;

    /**
     * Find news by author name list.
     *
     * @param authorName the author name
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<News> findByAuthorName(String authorName) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_FIND_NEWS_BY_AUTHORS_NAME,
                    new MapSqlParameterSource()
                            .addValue(TABLE_AUTHORS_COLUMN_NAME, authorName),
                    newsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_AUTHORS_ID = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :authors_id;
            """;

    /**
     * Find news by author id list.
     *
     * @param authorId the author id
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<News> findByAuthorId(long authorId) throws RepositoryException {
        try {
            return jdbcTemplate.query(SELECT_FIND_NEWS_BY_AUTHORS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_AUTHORS_ID, authorId),
                    newsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e);
            throw new RepositoryException(e);
        }
    }

    private static final String SELECT_FIND_NEWS_BY_TITLE = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title = :title;
            """;

    /**
     * Is exists news with title.
     *
     * @param title the news title
     * @return true - if exists news with title, false - if not exists
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean isExistsNewsWithTitle(String title) throws RepositoryException {
        try {
            return !jdbcTemplate.query(
                            SELECT_FIND_NEWS_BY_TITLE,
                            new MapSqlParameterSource()
                                    .addValue(TABLE_NEWS_COLUMN_TITLE, title),
                            newsMapper)
                    .isEmpty();
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }
}