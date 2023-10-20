package com.mjc.school.repository;

import com.mjc.school.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = """
            SELECT COUNT(name) > 0
            FROM authors
            WHERE name = :name
            """, nativeQuery = true)
    boolean existsByName(@Param("name") String name);

    @Modifying
    @Query(value = """
            UPDATE authors
            SET name = :name
            WHERE id = :id
            """, nativeQuery = true)
    void update(@Param("id") Long id, @Param("name") String name);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfName(@Param("partOfName") String partOfName,
                                  @Param("indexFirstElement") Integer indexFirstElement,
                                  @Param("size") Integer size);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            """, nativeQuery = true)
    List<Author> findByPartOfName(@Param("partOfName") String partOfName);

    @Query(value = """
            SELECT authors.id, authors.name
            FROM authors INNER JOIN news
            ON authors.id = news.authors_id
            WHERE news.id = :newsId
            """, nativeQuery = true)
    Optional<Author> findByNewsId(@Param("newsId") Long newsId);

    @Query(value = """
             SELECT authors.id, authors.name
             FROM authors LEFT JOIN news
             ON authors.id = news.authors_id
             GROUP BY authors.id
             ORDER BY COUNT(news.authors_id) DESC
             LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<Author> sortAllAuthorsWithAmountWrittenNewsDesc(@Param("indexFirstElement") Integer indexFirstElement,
                                                         @Param("size") Integer size);

    @Query(value = """
             SELECT authors.id, authors.name
             FROM authors LEFT JOIN news
             ON authors.id = news.authors_id
             GROUP BY authors.id
             ORDER BY COUNT(news.authors_id) DESC
            """, nativeQuery = true)
    List<Author> sortAllAuthorsWithAmountWrittenNewsDesc();
}