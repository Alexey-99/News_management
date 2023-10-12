package com.mjc.school.repository.impl.tag.impl;

import com.mjc.school.entity.Tag;
import com.mjc.school.repository.impl.CRUDOperationRepositoryImpl;
import com.mjc.school.repository.impl.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TagRepositoryImpl
        extends CRUDOperationRepositoryImpl<Tag>
        implements TagRepository {
    private final EntityManager entityManager;

    @Autowired
    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    private static final String QUERY_INSERT_TAG = """
            INSERT INTO tags (name)
            VALUES (:name);
            """;

    @Override
    public boolean create(Tag tag) {
        return false;
    }

    private static final String QUERY_INSERT_TAG_TO_NEWS = """
            INSERT INTO news_tags (news_id, tags_id)
            VALUES (:news_id, :tags_id);
            """;

    @Override
    public boolean addToNews(long tagId, long newsId)  {
            return false;
    }

    private static final String QUERY_DELETE_TAG_FROM_NEWS = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id
                AND tags_id = :tags_id;
            """;

    @Override
    public boolean removeFromNews(long tagId, long newsId) {
            return false;
    }

    private static final String QUERY_DELETE_TAG_BY_TAGS_ID_FROM_TABLE_NEWS_TAGS = """
            DELETE
            FROM news_tags
            WHERE tags_id = :tags_id;
            """;

    @Override
    public boolean deleteAllTagsFromNewsByNewsId(long tagId) {
            return false;
    }

    private static final String QUERY_SELECT_TAG_BY_NEWS_ID = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON tags.id = news_tags.tags_id
            WHERE news_tags.news_id = :news_id;
            """;

    @Override
    public List<Tag> findByNewsId(long newsId, int page, int size) {
        return null;
    }

    private static final String QUERY_SELECT_TAG_BY_NAME = """
            SELECT id, name
            FROM tags
            WHERE name = :name;
            """;

    @Override
    public boolean isExistsTagWithName(String name)  {
            return false;
    }

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
}