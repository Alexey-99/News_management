package com.mjc.school.repository;

import com.mjc.school.model.Comment;
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
            ORDER BY created ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortCreatedAsc(@Param("size") Integer size,
                                        @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            ORDER BY created DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortCreatedDesc(@Param("size") Integer size,
                                         @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            ORDER BY modified ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortModifiedAsc(@Param("size") Integer size,
                                         @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            ORDER BY modified DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortModifiedDesc(@Param("size") Integer size,
                                          @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortIdAsc(@Param("size") Integer size,
                                   @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findAllSortIdDesc(@Param("size") Integer size,
                                    @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT COUNT(id)
            FROM comments
            """, nativeQuery = true)
    Long countAllComments();

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY created ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortCreatedAsc(@Param("newsId") Long newsId,
                                             @Param("size") Integer size,
                                             @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY created DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortCreatedDesc(@Param("newsId") Long newsId,
                                              @Param("size") Integer size,
                                              @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortIdAsc(@Param("newsId") Long newsId,
                                        @Param("size") Integer size,
                                        @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortIdDesc(@Param("newsId") Long newsId,
                                         @Param("size") Integer size,
                                         @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY modified ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortModifiedAsc(@Param("newsId") Long newsId,
                                              @Param("size") Integer size,
                                              @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY modified DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortModifiedDesc(@Param("newsId") Long newsId,
                                               @Param("size") Integer size,
                                               @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT COUNT(id)
            FROM comments
            WHERE news_id = :newsId
            """, nativeQuery = true)
    Long countAllCommentsByNewsId(@Param("newsId") Long newsId);

    @Query(value = """
            SELECT id, content, news_id, created, modified
            FROM comments
            WHERE news_id = :newsId
            ORDER BY modified DESC
            """, nativeQuery = true)
    List<Comment> findByNewsIdSortModifiedDesc(@Param("newsId") Long newsId);
}