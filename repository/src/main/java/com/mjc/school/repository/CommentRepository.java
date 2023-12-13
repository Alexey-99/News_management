package com.mjc.school.repository;

import com.mjc.school.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query(value = """
            UPDATE comments
            SET content = :content,
                news_id = :news_id,
                modified = :modified
            WHERE id = :id
            """, nativeQuery = true)
    void update(@Param("content") String content,
                @Param("news_id") Long newsId,
                @Param("modified") String modified,
                @Param("id") Long commentId);

    @Modifying
    @Query(value = """
            DELETE
            FROM comments
            WHERE news_id = :news_id
            """, nativeQuery = true)
    void deleteByNewsId(@Param("news_id") Long newsId);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            """, nativeQuery = true)
    List<Comment> findByNewsId(@Param("newsId") Long newsId, Pageable pageable);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            """, nativeQuery = true)
    List<Comment> findAllList(Pageable pageable);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            """, nativeQuery = true)
    List<Comment> findByNewsId(@Param("newsId") Long newsId);

    @Query(value = """
            SELECT COUNT(id)
            FROM comments
            WHERE news_id = :newsId
            """, nativeQuery = true)
    Long countAllCommentsByNewsId(@Param("newsId") Long newsId);

    @Query(value = """
            SELECT COUNT(id)
            FROM comments
            """, nativeQuery = true)
    Long countAllComments();
}