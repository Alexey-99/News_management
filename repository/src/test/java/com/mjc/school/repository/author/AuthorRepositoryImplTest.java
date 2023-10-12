package com.mjc.school.repository.author;

import com.mjc.school.config.DataBaseConfigTest;
import com.mjc.school.entity.Author;

import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.repository.impl.news.NewsRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataBaseConfigTest.class,
        loader = AnnotationConfigContextLoader.class)
class AuthorRepositoryImplTest {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;
    private List<Author> listAllAuthors;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM authors;", new MapSqlParameterSource());
        Author author1 = Author.builder().id(1).name("Alex").build();
        Author author2 = Author.builder().id(2).name("Max").build();
        Author author3 = Author.builder().id(3).name("Jon").build();
        Author author4 = Author.builder().id(4).name("Tony").build();
        Author author5 = Author.builder().id(5).name("Tom").build();
        Author author6 = Author.builder().id(6).name("Alisa").build();
        authorRepository.create(author1);
        authorRepository.create(author2);
        authorRepository.create(author3);
        authorRepository.create(author4);
        authorRepository.create(author5);
        authorRepository.create(author6);
        listAllAuthors = authorRepository.findAll();
    }

    @Test
    @DisplayName("""
            create author.
            """)
    void createTest() { // 6
        boolean actualResult = authorRepository.create(
                Author.builder()
                        .name("Kristina")
                        .build());
        assertTrue(actualResult);
    }

    @Test
    @DisplayName("""
            delete author by id.
            """)
    void deleteByIdTest() { // 6-1
        boolean actual = authorRepository.deleteById(
                listAllAuthors.get(0).getId());
        assertTrue(actual);

    }

    @Test
    void updateTest() { // 5
        Author authorUpdating = listAllAuthors.get(0);
        authorUpdating.setName("Angel");
        Author actual = authorRepository.update(authorUpdating);
        // assertTrue(actual);
    }

    @Test
    void findAllTest() { //5
        List<Author> actual = authorRepository.findAll();
        assertEquals(listAllAuthors, actual);
    }

    @Test
    void findByIdTest() { // 5
        Author actual = authorRepository.findById(listAllAuthors.get(0).getId());
        assertEquals(listAllAuthors.get(0), actual);
    }

    @Test
    void findByNewsIdTest() { // 5
        Author actual = authorRepository.findByNewsId(0);
        assertEquals(null, actual);
    }

    @Test
    void selectAllAuthorsIdWithAmountOfWrittenNewsTest() { // 5
//        List<AuthorIdWithAmountOfWrittenNews> actual =
//                authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews();
//        assertEquals(null, actual);
    }

    @Test
    void isExistsAuthorWithNameTest() {
        boolean actual = authorRepository.isNotExistsAuthorWithName(
                listAllAuthors.get(0).getName());
        assertTrue(actual);
    }

    @AfterAll
    static void afterAll() {

    }
}