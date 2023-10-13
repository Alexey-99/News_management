package com.mjc.school.repository.impl.author;

import com.mjc.school.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
//    Author findByNewsId(long newsId);

//    boolean isNotExistsAuthorWithName(String name);

//    boolean create(Author entity);

//    boolean deleteById(long id);

    @Modifying
    @Query(value = """
            UPDATE authors a
            SET a.name = :name
            WHERE a.id = :id;
            """)
    void update(@Param("id") Long id, @Param("name") String name);

    @Query(value = """
            SELECT a
            FROM authors a
            WHERE a.name LIKE :partOfName
            LIMIT :size OFFSET :indexFirstElement;
            """)
    List<Author> findByPartOfName(@Param("partOfName") String partOfName,
                                  @Param("indexFirstElement") Integer indexFirstElement,
                                  @Param("size") Integer size);

//    List<Author> findAll(int page, int size);

//    List<Author> findAll();

    //Author findById(long id);
}