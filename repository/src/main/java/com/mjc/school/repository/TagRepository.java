package com.mjc.school.repository;

import com.mjc.school.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            SELECT COUNT(id)
            FROM tags
            """, nativeQuery = true)
    Long countAll();

    @Query(value = """
            SELECT COUNT(id)
            FROM tags
            WHERE name LIKE :part_name
            """, nativeQuery = true)
    Long countAllByPartOfName(@Param("part_name") String partOfName);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            ORDER BY tags.name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByNewsIdSortNameAsc(@Param("news_id") Long newsId,
                                      @Param("size") Integer size,
                                      @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            ORDER BY tags.name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByNewsIdSortNameDesc(@Param("news_id") Long newsId,
                                       @Param("size") Integer size,
                                       @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            ORDER BY tags.id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByNewsIdSortIdAsc(@Param("news_id") Long newsId,
                                    @Param("size") Integer size,
                                    @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags
                INNER JOIN news_tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            ORDER BY tags.id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByNewsIdSortIdDesc(@Param("news_id") Long newsId,
                                     @Param("size") Integer size,
                                     @Param("numberFirstElement") Integer numberFirstElement);

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
            SELECT COUNT(news.id)
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            """, nativeQuery = true)
    Long countAllByNewsId(@Param("news_id") Long newsId);

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortNameAsc(@Param("size") Integer size,
                                 @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortNameDesc(@Param("size") Integer size,
                                  @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY name ASC
            """, nativeQuery = true)
    List<Tag> findAllSortNameAsc();

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY name DESC
            """, nativeQuery = true)
    List<Tag> findAllSortNameDesc();

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortIdAsc(@Param("size") Integer size,
                               @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortIdDesc(@Param("size") Integer size,
                                @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortCountNewsAsc(@Param("size") Integer size,
                                      @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findAllSortCountNewsDesc(@Param("size") Integer size,
                                       @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) ASC
            """, nativeQuery = true)
    List<Tag> findAllSortCountNewsAsc();

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) DESC
            """, nativeQuery = true)
    List<Tag> findAllSortCountNewsDesc();


    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            ORDER BY name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortNameAsc(@Param("part_name") String partOfName,
                                          @Param("size") Integer size,
                                          @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            ORDER BY name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortNameDesc(@Param("part_name") String partOfName,
                                           @Param("size") Integer size,
                                           @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortIdAsc(@Param("part_name") String partOfName,
                                        @Param("size") Integer size,
                                        @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM tags
            WHERE name LIKE :part_name
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortIdDesc(@Param("part_name") String partOfName,
                                         @Param("size") Integer size,
                                         @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            WHERE name LIKE :part_name
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortCountNewsAsc(@Param("part_name") String partOfName,
                                               @Param("size") Integer size,
                                               @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT tags.id, tags.name
            FROM tags LEFT JOIN news_tags
            ON tags.id = news_tags.tags_id
            WHERE name LIKE :part_name
            GROUP BY tags.id
            ORDER BY COUNT(news_tags.tags_id) DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Tag> findByPartOfNameSortCountNewsDesc(@Param("part_name") String partOfName,
                                                @Param("size") Integer size,
                                                @Param("numberFirstElement") Integer numberFirstElement);


    @Query(value = """
            SELECT COUNT(name) = 0
            FROM tags
            WHERE name = :name
            """, nativeQuery = true)
    boolean notExistsByName(@Param("name") String name);
}