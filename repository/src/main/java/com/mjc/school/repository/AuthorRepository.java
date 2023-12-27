package com.mjc.school.repository;

import com.mjc.school.model.Author;
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
            SELECT COUNT(id)
            FROM authors
            """, nativeQuery = true)
    Long countAll();

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByIdAsc(@Param("partOfName") String partOfName,
                                         @Param("size") Integer size,
                                         @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByIdDesc(@Param("partOfName") String partOfName,
                                          @Param("size") Integer size,
                                          @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            ORDER BY name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByNameAsc(@Param("partOfName") String partOfName,
                                           @Param("size") Integer size,
                                           @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :partOfName
            ORDER BY name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByNameDesc(@Param("partOfName") String partOfName,
                                            @Param("size") Integer size,
                                            @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT authors.id, authors.name
            FROM authors LEFT JOIN news
            ON authors.id = news.authors_id
            WHERE name LIKE :partOfName
            GROUP BY authors.id
            ORDER BY COUNT(news.authors_id) ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByCountNewsAsc(@Param("partOfName") String partOfName,
                                                @Param("size") Integer size,
                                                @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT authors.id, authors.name
            FROM authors LEFT JOIN news
            ON authors.id = news.authors_id
            WHERE name LIKE :partOfName
            GROUP BY authors.id
            ORDER BY COUNT(news.authors_id) DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findByPartOfNameByCountNewsDesc(@Param("partOfName") String partOfName,
                                                 @Param("size") Integer size,
                                                 @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            WHERE name = :name
            """, nativeQuery = true)
    Optional<Author> findByName(@Param("name") String name);

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
            SELECT id, name
            FROM authors
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByIdAsc(@Param("size") Integer size,
                                @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            ORDER BY id DESC
            limit :size offset :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByIdDesc(@Param("size") Integer size,
                                 @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            ORDER BY name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByNameAsc(@Param("size") Integer size,
                                  @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, name
            FROM authors
            ORDER BY name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByNameDesc(@Param("size") Integer size,
                                   @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT authors.id, authors.name
            FROM authors LEFT JOIN news
            ON authors.id = news.authors_id
            GROUP BY authors.id
            ORDER BY COUNT(news.authors_id) ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByCountNewsAsc(@Param("size") Integer size,
                                       @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT authors.id, authors.name
            FROM authors LEFT JOIN news
            ON authors.id = news.authors_id
            GROUP BY authors.id
            ORDER BY COUNT(news.authors_id) DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<Author> findAllByCountNewsDesc(@Param("size") Integer size,
                                        @Param("numberFirstElement") Integer numberFirstElement);
}