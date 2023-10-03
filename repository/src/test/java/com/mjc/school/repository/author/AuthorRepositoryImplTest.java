package com.mjc.school.repository.author;

import com.mjc.school.config.DataBaseConfigTest;
import com.mjc.school.config.TestConfig;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.impl.AuthorRepositoryImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        DataBaseConfigTest.class,
        loader = AnnotationConfigContextLoader.class)
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepository authorRepository;

    private List<Author> listAllAuthors;

    @BeforeEach
    void setUp() throws RepositoryException {
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
        boolean actual = authorRepository.update(authorUpdating);
        assertTrue(actual);
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
        boolean actual = authorRepository.isExistsAuthorWithName(
                listAllAuthors.get(0).getName());
        assertTrue(actual);
    }
}