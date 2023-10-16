package com.mjc.school.repository.impl;

import com.mjc.school.NewsTag;
import com.mjc.school.Tag;
import com.mjc.school.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag, Long> {
    @Modifying
    @Query(value = """
            INSERT INTO news_tags (news_id, tags_id)
            VALUES (:news_id, :tags_id)
            """, nativeQuery = true)
    void addToNews(@Param("tags_id") Long tagId,
                   @Param("news_id") Long newsId);

    @Modifying
    @Query(value = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id
                AND tags_id = :tags_id
            """, nativeQuery = true)
    void deleteFromNews(@Param("tags_id") Long tagId,
                        @Param("news_id") Long newsId);

    @Modifying
    @Query(value = """
            DELETE
            FROM news_tags
            WHERE tags_id = :tags_id
            """, nativeQuery = true)
    void deleteFromAllNewsById(@Param("tags_id") Long tagId);

    @Modifying
    @Query(value = """
            UPDATE tags
            SET name = :tag_name
            WHERE id = :tag_id
            """, nativeQuery = true)
    void update(@Param("tag_name") String tagName,
                @Param("tag_id") Long tagId);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfName(@Param("part_name") String partOfName,
                               @Param("indexFirstElement") Integer indexFirstElement,
                               @Param("size") Integer size);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            """, nativeQuery = true)
    List<Tag> findByPartOfName(@Param("part_name") String partOfName);

    @Query(value = """
            SELECT news_tags.id, news_tags.news_id, news_tags.tags_id
            FROM tags
                INNER JOIN news_tags
                    ON tags.id = news_tags.tags_id
            WHERE news_tags.news_id = :news_id
            LIMIT :size OFFSET :indexFirstElement
            """)
    List<NewsTag> findByNewsId(@Param("news_id") Long newsId,
                               @Param("indexFirstElement") Integer indexFirstElement,
                               @Param("size") Integer size);

    @Query(value = """
            SELECT news_tags.id, news_tags.news_id, news_tags.tags_id
            FROM tags
                INNER JOIN news_tags
                    ON tags.id = news_tags.tags_id
            WHERE news_tags.news_id = :news_id
            """)
    List<NewsTag> findByNewsId(@Param("news_id") Long newsId);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name = :name
            """, nativeQuery = true)
    Optional<Tag> findByName(@Param("name") String name);
}