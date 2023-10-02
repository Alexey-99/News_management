package com.mjc.school.repository.author;

import com.mjc.school.config.DataBaseConfigTest;
import com.mjc.school.entity.Author;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.impl.AuthorRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = DataBaseConfigTest.class)
class AuthorRepositoryImplTest {
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    private static AuthorRepositoryImpl authorRepository;

    @Test
    @DisplayName("""
            create author.
            """)
    void createTest() {
        boolean actualResult = false;
        try {
            System.out.println(jdbcTemplate.hashCode());
            actualResult = authorRepository.create(
                    new Author.AuthorBuilder()
                            .setName("Kristina")
                            .build());
            assertTrue(actualResult);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("""
            delete author by id.
            """)
    void deleteByIdTest() {
        try {
            authorRepository.deleteById(1);
        } catch (RepositoryException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateTest() {
    }

    @Test
    void findAllTest() {
    }

    @Test
    void findByIdTest() throws RepositoryException {
        authorRepository.findById(1);
    }

    @Test
    void findByNewsIdTest() {
    }

    @Test
    void selectAllAuthorsIdWithAmountOfWrittenNewsTest() {
    }

    @Test
    void isExistsAuthorWithNameTest() {
    }
}