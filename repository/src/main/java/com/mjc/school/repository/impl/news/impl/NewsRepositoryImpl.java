package com.mjc.school.repository.impl.news.impl;

import com.mjc.school.entity.News;
import com.mjc.school.repository.impl.CRUDOperationRepositoryImpl;
import com.mjc.school.repository.impl.news.NewsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class NewsRepositoryImpl
        extends CRUDOperationRepositoryImpl<News>
        implements NewsRepository {
    private static final Logger log = LogManager.getLogger();
    private final EntityManager entityManager;

    @Autowired
    public NewsRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public boolean create(News news) {
        entityManager.persist(news);
        return findById(news.getId()) != null;
    }

    private static final String QUERY_DELETE_NEWS_BY_AUTHOR_ID = """
            DELETE FROM news
            WHERE authors_id = :authors_id;
            """;

    @Override
    public boolean deleteByAuthorId(long authorId) {
            return false;
    }

    private static final String QUERY_DELETE_NEWS_ID_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id;
            """;


    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long newsId) {
            return false;
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


    @Override
    public List<News> findByTagName(String tagName, int page, int size) {
            return null;
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


    @Override
    public List<News> findByTagId(long tagId, int page, int size)  {
            return null;
    }

    private static final String SELECT_FIND_NEWS_BY_AUTHORS_NAME = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name = :name;
            """;

    @Override
    public List<News> findByAuthorName(String authorName, int page, int size) {
            return null;
    }

    private static final String SELECT_FIND_NEWS_BY_AUTHORS_ID = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :authors_id;
            """;

    @Override
    public List<News> findByAuthorId(long authorId, int page, int size) {
            return null;
    }

    private static final String SELECT_FIND_NEWS_BY_TITLE = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title = :title;
            """;

    @Override
    public boolean isExistsNewsWithTitle(String title) {
            return false;
    }

    @Override
    protected Class<News> getEntityClass() {
        return News.class;
    }
}