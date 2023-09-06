package com.mjc.school.repository.author.impl;

import com.mjc.school.config.mapper.AuthorMapper;
import com.mjc.school.config.mapper.AuthorIdWithAmountOfWrittenNewsMapper;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_ID;
import static com.mjc.school.name.ColumnName.TABLE_AUTHORS_COLUMN_NAME;
import static com.mjc.school.name.ColumnName.TABLE_NEWS_COLUMN_ID;
import static org.apache.logging.log4j.Level.ERROR;

/**
 * The type Author repository.
 */
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    @Qualifier("namedJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private AuthorIdWithAmountOfWrittenNewsMapper authorIdWithAmountOfWrittenNewsMapper;

    private static final String QUERY_INSERT_AUTHOR = """
            INSERT INTO authors(name)
            VALUES (:name);
            """;

    /**
     * Create author.
     *
     * @param author the author
     * @return the boolean
     */
    @Override
    public boolean create(Author author) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_INSERT_AUTHOR,
                    new MapSqlParameterSource()
                            .addValue(TABLE_AUTHORS_COLUMN_NAME, author.getName())) > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_DELETE_AUTHOR = """
            DELETE
            FROM authors
            WHERE id = :id;
            """;

    /**
     * Delete author.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_DELETE_AUTHOR,
                    new MapSqlParameterSource()
                            .addValue(TABLE_AUTHORS_COLUMN_ID, id))
                    > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_UPDATE_AUTHOR = """
            UPDATE authors
            SET name = :name
            WHERE id = :id;
            """;

    /**
     * Update author.
     *
     * @param author the author
     * @return the boolean
     */
    @Override
    public boolean update(Author author) throws RepositoryException {
        try {
            return jdbcTemplate.update(QUERY_UPDATE_AUTHOR,
                    new MapSqlParameterSource()
                            .addValue(TABLE_AUTHORS_COLUMN_ID, author.getId())
                            .addValue(TABLE_AUTHORS_COLUMN_NAME, author.getName())) > 0;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_ALL_AUTHORS = """
            SELECT id, name
            FROM authors;
            """;

    /**
     * Find all authors list.
     *
     * @return the list
     */
    @Override
    public List<Author> findAllAuthors() throws RepositoryException {
        try {
            return jdbcTemplate.query(QUERY_SELECT_ALL_AUTHORS, authorMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_AUTHOR_BY_ID = """
            SELECT id, name
            FROM authors
            WHERE id = :id;
            """;

    /**
     * Find by id author.
     *
     * @param id the id
     * @return the author
     */
    @Override
    public Author findById(long id) throws RepositoryException {
        try {
            List<Author> authorListResult = jdbcTemplate.query(QUERY_SELECT_AUTHOR_BY_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_AUTHORS_COLUMN_ID, id), authorMapper);
            return !authorListResult.isEmpty() ? authorListResult.get(0) : null;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_AUTHOR_BY_NEWS_ID = """
            SELECT authors.id , authors.name
            FROM authors INNER JOIN news
            ON authors.id = news.authors_id
            WHERE news.id = :id;
            """;

    /**
     * Find by news id author.
     *
     * @param newsId the news id
     * @return the author
     */
    @Override
    public Author findByNewsId(long newsId) throws RepositoryException {
        try {
            List<Author> authorListResult = jdbcTemplate.query(
                    QUERY_SELECT_AUTHOR_BY_NEWS_ID,
                    new MapSqlParameterSource()
                            .addValue(TABLE_NEWS_COLUMN_ID, newsId),
                    authorMapper);
            return !authorListResult.isEmpty() ?
                    authorListResult.get(0) :
                    null;
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }

    }

    private static final String QUERY_SELECT_ALL_AUTHORS_ID_WITH_AMOUNT_WRITTEN_NEWS = """
            SELECT authors.id, COUNT(news.authors_id)
            FROM authors LEFT JOIN news
            ON authors.id = news.authors_id
            GROUP BY authors.id;
            """;

    /**
     * Select all authors id with amount of written news list.
     *
     * @return the list
     * @throws RepositoryException the repository exception
     */
    @Override
    public List<AuthorIdWithAmountOfWrittenNews>
    selectAllAuthorsIdWithAmountOfWrittenNews()
            throws RepositoryException {
        try {
            return jdbcTemplate.query(
                    QUERY_SELECT_ALL_AUTHORS_ID_WITH_AMOUNT_WRITTEN_NEWS,
                    authorIdWithAmountOfWrittenNewsMapper);
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    private static final String QUERY_SELECT_AUTHOR_BY_NAME = """
            SELECT id, name
            FROM authors
            WHERE id = :id;
            """;

    /**
     * Is exists author with name.
     *
     * @param name the name
     * @return true - if exists author with name, false - if not exists
     * @throws RepositoryException the repository exception
     */
    @Override
    public boolean isExistsAuthorWithName(String name)
            throws RepositoryException {
        try {
            return !jdbcTemplate.query(
                            QUERY_SELECT_AUTHOR_BY_NAME,
                            new MapSqlParameterSource()
                                    .addValue(TABLE_AUTHORS_COLUMN_NAME, name),
                            authorMapper)
                    .isEmpty();
        } catch (DataAccessException e) {
            log.log(ERROR, e.getMessage());
            throw new RepositoryException(e);
        }
    }
}