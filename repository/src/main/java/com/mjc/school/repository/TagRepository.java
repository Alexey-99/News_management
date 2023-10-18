package com.mjc.school.repository;

import com.mjc.school.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

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
            SELECT tags.id, tags.name
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<Tag> findByNewsId(@Param("news_id") Long newsId,
                               @Param("indexFirstElement") Integer indexFirstElement,
                               @Param("size") Integer size);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            """, nativeQuery = true)
    List<Tag> findByNewsId(@Param("news_id") Long newsId);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name = :name
            """, nativeQuery = true)
    Optional<Tag> findByName(@Param("name") String name);
}