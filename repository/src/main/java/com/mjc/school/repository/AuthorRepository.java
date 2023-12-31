package com.mjc.school.repository;

import com.mjc.school.model.Author;
import org.springframework.data.domain.Pageable;
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
            SELECT COUNT(name) = 0
            FROM authors
            WHERE name = :name
            """, nativeQuery = true)
    boolean notExistsByName(@Param("name") String name);

    @Modifying
    @Query(value = """
            UPDATE authors
            SET name = :name
            WHERE id = :id
            """, nativeQuery = true)
    void update(@Param("id") Long id, @Param("name") String name);

    @Query(value = """
            SELECT COUNT(id)
            FROM authors
            """, nativeQuery = true)
    Long countAll();

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            """, nativeQuery = true)
    List<Author> findByPartOfName(@Param("partOfName") String partOfName, Pageable pageable);

    @Query(value = """
            SELECT COUNT(id)
            FROM authors
            WHERE name LIKE :partOfName
            """, nativeQuery = true)
    Long countAllByPartOfName(@Param("partOfName") String partOfName);

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
             ORDER BY COUNT(news.authors_id) ASC
            """, nativeQuery = true)
    List<Author> findAllAuthorsWithAmountWrittenNewsAsc(Pageable pageable);

    @Query(value = """
             SELECT authors.id, authors.name
             FROM authors LEFT JOIN news
             ON authors.id = news.authors_id
             GROUP BY authors.id
             ORDER BY COUNT(news.authors_id) DESC
            """, nativeQuery = true)
    List<Author> findAllAuthorsWithAmountWrittenNewsDesc(Pageable pageable);

    @Query(value = """
            SELECT id, name
            FROM authors
            """, nativeQuery = true)
    List<Author> findAllList(Pageable pageable);
}