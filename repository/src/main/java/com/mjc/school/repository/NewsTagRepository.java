package com.mjc.school.repository;

import com.mjc.school.model.NewsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
    @Query(value = """
            SELECT news_tags.id, news_tags.news_id, news_tags.tags_id
            FROM news_tags
                INNER JOIN tags
                    ON  news_tags.tags_id = tags.id
            WHERE news_tags.news_id = :news_id
            ORDER BY tags.name ASC
            """, nativeQuery = true)
    List<NewsTag> findByNewsIdSortNameAsc(@Param("news_id") Long newsId);

}