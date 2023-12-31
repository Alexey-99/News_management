package com.mjc.school.repository;

import com.mjc.school.model.NewsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
    @Query(value = """
            SELECT id, news_id, tags_id
            FROM news_tags
            WHERE news_id = :news_id
            """, nativeQuery = true)
    List<NewsTag> findByNewsId(@Param("news_id") Long newsId);
}