package com.mjc.school.repository.author;

import com.mjc.school.config.DataBaseConfigTest;
import com.mjc.school.entity.Author;
import com.mjc.school.exception.RepositoryException;

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
    void setUp() throws RepositoryException {
        jdbcTemplate.update("DELETE FROM authors;", new MapSqlParameterSource());
        Author author1 = new Author.AuthorBuilder().setId(1).setName("Alex").build();
        Author author2 = new Author.AuthorBuilder().setId(2).setName("Max").build();
        Author author3 = new Author.AuthorBuilder().setId(3).setName("Jon").build();
        Author author4 = new Author.AuthorBuilder().setId(4).setName("Tony").build();
        Author author5 = new Author.AuthorBuilder().setId(5).setName("Tom").build();
        Author author6 = new Author.AuthorBuilder().setId(6).setName("Alisa").build();
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
    void createTest() throws RepositoryException { // 6
        boolean actualResult = authorRepository.create(
                new Author.AuthorBuilder()
                        .setName("Kristina")
                        .build());
        assertTrue(actualResult);
    }

    @Test
    @DisplayName("""
            delete author by id.
            """)
    void deleteByIdTest() throws RepositoryException { // 6-1
        boolean actual = authorRepository.deleteById(
                listAllAuthors.get(0).getId());
        assertTrue(actual);

    }

    @Test
    void updateTest() throws RepositoryException { // 5
        Author authorUpdating = listAllAuthors.get(0);
        authorUpdating.setName("Angel");
        Author actual = authorRepository.update(authorUpdating);
        // assertTrue(actual);
    }

    @Test
    void findAllTest() throws RepositoryException { //5
        List<Author> actual = authorRepository.findAll();
        assertEquals(listAllAuthors, actual);
    }

    @Test
    void findByIdTest() throws RepositoryException { // 5
        Author actual = authorRepository.findById(listAllAuthors.get(0).getId());
        assertEquals(listAllAuthors.get(0), actual);
    }

    @Test
    void findByNewsIdTest() throws RepositoryException { // 5
        Author actual = authorRepository.findByNewsId(0);
        assertEquals(null, actual);
    }

    @Test
    void selectAllAuthorsIdWithAmountOfWrittenNewsTest() throws RepositoryException { // 5
//        List<AuthorIdWithAmountOfWrittenNews> actual =
//                authorRepository.selectAllAuthorsIdWithAmountOfWrittenNews();
//        assertEquals(null, actual);
    }

    @Test
    void isExistsAuthorWithNameTest() throws RepositoryException {
        boolean actual = authorRepository.isNotExistsAuthorWithName(
                listAllAuthors.get(0).getName());
        assertTrue(actual);
    }

    @AfterAll
    static void afterAll() {

    }
}