package com.mjc.school.repository.impl.news;

import com.mjc.school.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    boolean deleteByAuthorId(long authorId);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    boolean deleteAllTagsFromNewsByNewsId(long newsId);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    List<News> findByTagName(String tagName, int page, int size);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    List<News> findByTagId(long tagId, int page, int size);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    List<News> findByAuthorName(String authorName, int page, int size);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    List<News> findByAuthorId(long authorId, int page, int size);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    boolean isExistsNewsWithTitle(String title);

    //boolean create(News entity);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    boolean deleteById(long id);

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    News update(News entity);

    //List<News> findAll(int page, int size);

    //List<News> findAll();

//    @Query(value = """
//            SELECT a
//            FROM authors a
//            WHERE a.name LIKE :partOfName
//            LIMIT :size OFFSET :indexFirstElement
//            """, nativeQuery = true)
//    News findById(long id);
}