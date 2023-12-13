package com.mjc.school.repository;

import com.mjc.school.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Modifying
    @Query(value = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id
            """, nativeQuery = true)
    void deleteAllTagsFromNewsByNewsId(@Param("news_id") Long newsId);

    @Modifying
    @Query(value = """
            UPDATE news
            SET title = :title,
                content = :content,
                authors_id = :authors_id,
                modified = :modified
            WHERE id = :news_id
             """, nativeQuery = true)
    void update(@Param("title") String title,
                @Param("content") String content,
                @Param("authors_id") Long authorId,
                @Param("modified") String modified,
                @Param("news_id") Long newsId);

    @Query(value = """
            SELECT COUNT(id)
            FROM news
            """, nativeQuery = true)
    Long countAllNews();

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id, news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.name = :tag_name
            ORDER BY news.created ASC
            """, nativeQuery = true)
    List<News> findByTagNameCreatedAsc(@Param("tag_name") String tagName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id, news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.name = :tag_name
            ORDER BY news.created DESC
            """, nativeQuery = true)
    List<News> findByTagNameCreatedDesc(@Param("tag_name") String tagName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id, news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.name = :tag_name
            ORDER BY news.modified ASC
            """, nativeQuery = true)
    List<News> findByTagNameModifiedAsc(@Param("tag_name") String tagName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id, news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.name = :tag_name
            ORDER BY news.modified DESC
            """, nativeQuery = true)
    List<News> findByTagNameModifiedDesc(@Param("tag_name") String tagName, Pageable pageable);

    @Query(value = """
            SELECT COUNT(news.id)
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.name = :tag_name
            """, nativeQuery = true)
    Long countAllNewsByTagName(@Param("tag_name") String tagName);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
            WHERE news_tags.tags_id = :tag_id
            ORDER BY news.created ASC
            """, nativeQuery = true)
    List<News> findByTagIdCreatedAsc(@Param("tag_id") Long tagId, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
            WHERE news_tags.tags_id = :tag_id
            ORDER BY news.created DESC
            """, nativeQuery = true)
    List<News> findByTagIdCreatedDesc(@Param("tag_id") Long tagId, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
            WHERE news_tags.tags_id = :tag_id
            ORDER BY news.modified ASC
            """, nativeQuery = true)
    List<News> findByTagIdModifiedAsc(@Param("tag_id") Long tagId, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
            WHERE news_tags.tags_id = :tag_id
            ORDER BY news.modified DESC
            """, nativeQuery = true)
    List<News> findByTagIdModifiedDesc(@Param("tag_id") Long tagId, Pageable pageable);

    @Query(value = """
            SELECT COUNT(news.id)
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE tags.id = :tag_id
            """, nativeQuery = true)
    Long countAllNewsByTagId(@Param("tag_id") Long tagId);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            ORDER BY news.created ASC
            """, nativeQuery = true)
    List<News> findByPartOfAuthorNameCreatedAsc(@Param("part_author_name") String partOfAuthorName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            ORDER BY news.created DESC
            """, nativeQuery = true)
    List<News> findByPartOfAuthorNameCreatedDesc(@Param("part_author_name") String partOfAuthorName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            ORDER BY news.modified ASC
            """, nativeQuery = true)
    List<News> findByPartOfAuthorNameModifiedAsc(@Param("part_author_name") String partOfAuthorName, Pageable pageable);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
                news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            ORDER BY news.modified DESC
            """, nativeQuery = true)
    List<News> findByPartOfAuthorNameModifiedDesc(@Param("part_author_name") String partOfAuthorName, Pageable pageable);

    @Query(value = """
            SELECT COUNT(news.id)
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            """, nativeQuery = true)
    Long countAllNewsByPartOfAuthorName(@Param("part_author_name") String partOfAuthorName);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :author_id
            """, nativeQuery = true)
    List<News> findByAuthorId(@Param("author_id") Long authorId, Pageable pageable);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :author_id
            """, nativeQuery = true)
    List<News> findByAuthorId(@Param("author_id") Long authorId);

    @Query(value = """
            SELECT COUNT(id)
            FROM news
            WHERE authors_id = :author_id
            """, nativeQuery = true)
    Long countAllNewsByAuthorId(@Param("author_id") Long authorId);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title LIKE :part_of_title
            """, nativeQuery = true)
    List<News> findByPartOfTitle(@Param("part_of_title") String partOfTitle, Pageable pageable);

    @Query(value = """
            SELECT COUNT(id)
            FROM news
            WHERE title LIKE :part_of_title
            """, nativeQuery = true)
    Long countAllNewsByPartOfTitle(@Param("part_of_title") String partOfTitle);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE content LIKE :part_of_content
            """, nativeQuery = true)
    List<News> findByPartOfContent(@Param("part_of_content") String partOfContent, Pageable pageable);

    @Query(value = """
            SELECT COUNT(id)
            FROM news
            WHERE content LIKE :part_of_content
            """, nativeQuery = true)
    Long countAllNewsByPartOfContent(@Param("part_of_content") String partOfContent);

    @Query(value = """
            SELECT COUNT(title) = 0
            FROM news
            WHERE title = :title
            """, nativeQuery = true)
    boolean notExistsByTitle(@Param("title") String title);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            """, nativeQuery = true)
    List<News> findAllList(Pageable pageable);
}