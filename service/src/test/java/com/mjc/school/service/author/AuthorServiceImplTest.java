package com.mjc.school.service.author;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.News;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.impl.AuthorServiceImpl;
import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import com.mjc.school.validation.dto.Pagination;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorConverter authorConverter;
    @Mock
    private PaginationService paginationService;
    private static AuthorDTO authorDTOTesting;
    private static AuthorDTO authorDTOExpected;
    private static AuthorDTO authorDTOActual;

    @Test
    void create_when_authorNotExistsByName() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);
        boolean actualResult = authorService.create(authorDTOTesting);
        assertTrue(actualResult);
    }

    @Test
    void create_when_authorExistsByName() {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();
        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);
        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.create(authorDTOTesting)
        );
        assertEquals("tag_dto.name.not_valid.exists_tag_by_name", exception.getMessage());
    }

    @Test
    void deleteById_when_authorExistsById() {
        long authorId = 1;
        when(authorRepository.existsById(authorId)).thenReturn(true);
        boolean actualResult = authorService.deleteById(1);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_authorNotExistsById() {
        long authorId = 1;
        when(authorRepository.existsById(authorId)).thenReturn(false);
        boolean actualResult = authorService.deleteById(authorId);
        assertTrue(actualResult);
    }

    @Test
    void update_when_notFoundAuthorById() {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exception = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("service.exception.not_found_authors_by_id", exception.getMessage());
    }

    @Test
    void update_when_foundAuthorByIdAndAuthorNamesEqual() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_notExistsAuthorByName() throws ServiceBadRequestParameterException {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(true);

        authorDTOExpected = AuthorDTO.builder().id(1L).name("author_name_test_other").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.update(authorDTOTesting);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void update_when_foundAuthorById_and_AuthorNamesNotEqual_and_existsAuthorByName() {
        authorDTOTesting = AuthorDTO.builder().id(1L).name("author_name_test_other").build();

        Author authorFromDB = Author.builder().id(1L).name("author_name_test").news(List.of()).build();
        when(authorRepository.findById(authorDTOTesting.getId())).thenReturn(Optional.of(authorFromDB));

        when(authorRepository.notExistsByName(authorDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> authorService.update(authorDTOTesting));

        assertEquals("author_dto.name.not_valid.already_exists", exceptionActual.getMessage());
    }

    @Test
    void findAllWithPages_when_foundAuthors() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());
        when(authorRepository.findAllList(PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Author.builder().id(1).name("Alex").build(),
                        Author.builder().id(3).name("Bam").build(),
                        Author.builder().id(2).name("Sem").build()));

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll(page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @Test
    void findAllWithPages_when_notFoundAuthors() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(authorRepository.findAllList(
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findAll(page, size, sortField, sortType));

        assertEquals("service.exception.not_found_authors", exceptionActual.getMessage());
    }

    @Test
    void findAllAuthors() {
        when(authorConverter.toDTO(Author.builder().id(1).name("Alex").build()))
                .thenReturn(AuthorDTO.builder().id(1).name("Alex").build());
        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());
        when(authorRepository.findAll()).thenReturn(List.of(
                Author.builder().id(1).name("Alex").build(),
                Author.builder().id(3).name("Bam").build(),
                Author.builder().id(2).name("Sem").build()));

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(1).name("Alex").build(),
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findAll();
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @Test
    void countAllAuthors() {
        long countAllAuthors = 3L;

        when(authorRepository.countAll()).thenReturn(countAllAuthors);

        long countAllActual = authorService.countAll();
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }

    @Test
    void findById_when_foundAuthorById() throws ServiceNoContentException {
        long authorId = 1L;

        Author authorFromDB = Author.builder()
                .id(1).name("Alex").news(List.of())
                .build();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorFromDB));

        authorDTOExpected = AuthorDTO.builder().id(1).name("Alex").countNews(0).build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.findById(authorId);
        assertEquals(authorDTOExpected, authorDTOActual);
    }

    @Test
    void findById_when_notFoundAuthorById() {
        long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findById(authorId));

        assertEquals("service.exception.not_found_author_by_id", exceptionActual.getMessage());
    }

    @Test
    void findByPartOfNameWithPages_when_foundAuthors() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "m";

        when(authorConverter.toDTO(Author.builder().id(3).name("Bam").build()))
                .thenReturn(AuthorDTO.builder().id(3).name("Bam").build());
        when(authorConverter.toDTO(Author.builder().id(2).name("Sem").build()))
                .thenReturn(AuthorDTO.builder().id(2).name("Sem").build());

        when(authorRepository.findByPartOfName("%" + partOfName + "%",
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Author.builder().id(3).name("Bam").build(),
                        Author.builder().id(2).name("Sem").build()));

        List<AuthorDTO> authorDTOListExpected = List.of(
                AuthorDTO.builder().id(3).name("Bam").build(),
                AuthorDTO.builder().id(2).name("Sem").build());

        List<AuthorDTO> authorDTOListActual = authorService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(authorDTOListExpected, authorDTOListActual);
    }

    @Test
    void findByPartOfNameWithPages_when_notFoundAuthors() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "m";

        when(authorRepository.findByPartOfName("%" + partOfName + "%",
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findByPartOfName(partOfName, page, size, sortField, sortType));

        assertEquals("service.exception.not_found_authors_by_part_of_name", exceptionActual.getMessage());
    }

    @Test
    void countAllByPartOfName() {
        long countAllByPartOfName = 2L;
        String partOfName = "m";

        when(authorRepository.countAllByPartOfName("%" + partOfName + "%")).thenReturn(countAllByPartOfName);

        long countAllActual = authorService.countAllByPartOfName(partOfName);
        long countAllExpected = 2L;

        assertEquals(countAllExpected, countAllActual);
    }

    @Test
    void findByNewsId_when_foundAuthors() throws ServiceNoContentException {
        long newsId = 1L;

        Author authorFromDB = Author.builder()
                .id(1L)
                .name("author name test")
                .news(List.of(News.builder()
                        .id(1L)
                        .build()))
                .build();
        when(authorRepository.findByNewsId(newsId))
                .thenReturn(Optional.of(authorFromDB));

        authorDTOExpected = AuthorDTO.builder()
                .id(1L)
                .name("author name test")
                .countNews(1)
                .build();
        when(authorConverter.toDTO(authorFromDB)).thenReturn(authorDTOExpected);

        authorDTOActual = authorService.findByNewsId(newsId);
        assertEquals(authorDTOExpected, authorDTOActual);

    }

    @Test
    void findByNewsId_when_notFoundAuthors() {
        long newsId = 1L;
        when(authorRepository.findByNewsId(newsId)).thenReturn(Optional.empty());
        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findByNewsId(newsId));
        assertEquals("service.exception.not_found_authors_by_news_id", exceptionActual.getMessage());
    }

    @Test
    void findAllAuthorsIdWithAmountOfWrittenNews_when_sortTypeNull() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = null;

        List<Author> authorListFromDB = List.of(
                Author.builder().id(2).news(List.of(
                        News.builder().id(11).build(),
                        News.builder().id(12).build(),
                        News.builder().id(13).build(),
                        News.builder().id(14).build(),
                        News.builder().id(15).build(),
                        News.builder().id(16).build()
                )).build(),
                Author.builder().id(1).news(List.of(
                        News.builder().id(1).build(),
                        News.builder().id(3).build(),
                        News.builder().id(5).build(),
                        News.builder().id(6).build()
                )).build(),
                Author.builder().id(3).news(List.of(
                        News.builder().id(8).build(),
                        News.builder().id(10).build()
                )).build(),
                Author.builder().id(4).news(List.of()).build(),
                Author.builder().id(5).news(null).build()
        );
        when(authorRepository.findAllAuthorsWithAmountWrittenNewsDesc(PageRequest.of(numberFirstElement, size)))
                .thenReturn(authorListFromDB);
        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListExpected = List.of(
                new AuthorIdWithAmountOfWrittenNewsDTO(2, 6),
                new AuthorIdWithAmountOfWrittenNewsDTO(1, 4),
                new AuthorIdWithAmountOfWrittenNewsDTO(3, 2),
                new AuthorIdWithAmountOfWrittenNewsDTO(4, 0),
                new AuthorIdWithAmountOfWrittenNewsDTO(5, 0));

        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListActual =
                authorService.findAllAuthorsIdWithAmountOfWrittenNews(page, size, sortType);
        assertEquals(authorIdWithAmountOfWrittenNewsDTOListExpected, authorIdWithAmountOfWrittenNewsDTOListActual);
    }

    @Test
    void findAllAuthorsIdWithAmountOfWrittenNews_when_sortTypeASC() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";

        List<Author> authorListFromDB = List.of(
                Author.builder().id(4).news(List.of()).build(),
                Author.builder().id(5).news(null).build(),
                Author.builder().id(3).news(List.of(
                        News.builder().id(8).build(),
                        News.builder().id(10).build()
                )).build(),
                Author.builder().id(1).news(List.of(
                        News.builder().id(1).build(),
                        News.builder().id(3).build(),
                        News.builder().id(5).build(),
                        News.builder().id(6).build()
                )).build(),
                Author.builder().id(2).news(List.of(
                        News.builder().id(11).build(),
                        News.builder().id(12).build(),
                        News.builder().id(13).build(),
                        News.builder().id(14).build(),
                        News.builder().id(15).build(),
                        News.builder().id(16).build()
                )).build()
        );
        when(authorRepository.findAllAuthorsWithAmountWrittenNewsAsc(PageRequest.of(numberFirstElement, size)))
                .thenReturn(authorListFromDB);
        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListExpected = List.of(
                new AuthorIdWithAmountOfWrittenNewsDTO(4, 0),
                new AuthorIdWithAmountOfWrittenNewsDTO(5, 0),
                new AuthorIdWithAmountOfWrittenNewsDTO(3, 2),
                new AuthorIdWithAmountOfWrittenNewsDTO(1, 4),
                new AuthorIdWithAmountOfWrittenNewsDTO(2, 6)
        );

        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListActual =
                authorService.findAllAuthorsIdWithAmountOfWrittenNews(page, size, sortType);
        assertEquals(authorIdWithAmountOfWrittenNewsDTOListExpected, authorIdWithAmountOfWrittenNewsDTOListActual);
    }

    @Test
    void findAllAuthorsIdWithAmountOfWrittenNews_when_sortTypeDESC() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";

        List<Author> authorListFromDB = List.of(
                Author.builder().id(2).news(List.of(
                        News.builder().id(11).build(),
                        News.builder().id(12).build(),
                        News.builder().id(13).build(),
                        News.builder().id(14).build(),
                        News.builder().id(15).build(),
                        News.builder().id(16).build()
                )).build(),
                Author.builder().id(1).news(List.of(
                        News.builder().id(1).build(),
                        News.builder().id(3).build(),
                        News.builder().id(5).build(),
                        News.builder().id(6).build()
                )).build(),
                Author.builder().id(3).news(List.of(
                        News.builder().id(8).build(),
                        News.builder().id(10).build()
                )).build(),
                Author.builder().id(4).news(List.of()).build(),
                Author.builder().id(5).news(null).build()
        );
        when(authorRepository.findAllAuthorsWithAmountWrittenNewsDesc(PageRequest.of(numberFirstElement, size)))
                .thenReturn(authorListFromDB);
        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListExpected = List.of(
                new AuthorIdWithAmountOfWrittenNewsDTO(2, 6),
                new AuthorIdWithAmountOfWrittenNewsDTO(1, 4),
                new AuthorIdWithAmountOfWrittenNewsDTO(3, 2),
                new AuthorIdWithAmountOfWrittenNewsDTO(4, 0),
                new AuthorIdWithAmountOfWrittenNewsDTO(5, 0));

        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOListActual =
                authorService.findAllAuthorsIdWithAmountOfWrittenNews(page, size, sortType);
        assertEquals(authorIdWithAmountOfWrittenNewsDTOListExpected, authorIdWithAmountOfWrittenNewsDTOListActual);
    }

    @Test
    void findAllAuthorsIdWithAmountOfWrittenNews_when_sortTypeDESC_and_notFoundAuthors() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "DESC";

        List<Author> authorListFromDB = List.of();
        when(authorRepository.findAllAuthorsWithAmountWrittenNewsDesc(PageRequest.of(numberFirstElement, size)))
                .thenReturn(authorListFromDB);

        ServiceNoContentException exceptionActual = assertThrows(ServiceNoContentException.class,
                () -> authorService.findAllAuthorsIdWithAmountOfWrittenNews(page, size, sortType));

        assertEquals("service.exception.not_found_authors", exceptionActual.getMessage());
    }

    @Test
    void getPagination() {
        List<AuthorDTO> authorDTOList = List.of(
                AuthorDTO.builder().id(2).countNews(6).build(),
                AuthorDTO.builder().id(1).countNews(4).build(),
                AuthorDTO.builder().id(3).countNews(2).build(),
                AuthorDTO.builder().id(4).countNews(0).build(),
                AuthorDTO.builder().id(5).countNews(0).build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(countAllElements, size)).thenReturn(2);

        Pagination<AuthorDTO> authorDTOPaginationExpected = Pagination.<AuthorDTO>builder()
                .entity(authorDTOList)
                .size(size)
                .numberPage(page)
                .maxNumberPage(2)
                .build();
        Pagination<AuthorDTO> authorDTOPaginationActual =
                authorService.getPagination(authorDTOList, countAllElements, page, size);
        assertEquals(authorDTOPaginationExpected, authorDTOPaginationActual);
    }

    @Test
    void getPaginationAuthorIdWithAmountOfWrittenNews() {
        List<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOList =
                List.of(new AuthorIdWithAmountOfWrittenNewsDTO(2, 6),
                        new AuthorIdWithAmountOfWrittenNewsDTO(1, 4),
                        new AuthorIdWithAmountOfWrittenNewsDTO(3, 2),
                        new AuthorIdWithAmountOfWrittenNewsDTO(4, 0),
                        new AuthorIdWithAmountOfWrittenNewsDTO(5, 0));
        long countAllElements = 10L;
        int page = 1;
        int size = 5;

        when(paginationService.calcMaxNumberPage(countAllElements, size)).thenReturn(2);

        Pagination<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOPaginationExpected =
                Pagination.<AuthorIdWithAmountOfWrittenNewsDTO>builder()
                        .entity(authorIdWithAmountOfWrittenNewsDTOList)
                        .size(size)
                        .numberPage(page)
                        .maxNumberPage(2)
                        .build();
        Pagination<AuthorIdWithAmountOfWrittenNewsDTO> authorIdWithAmountOfWrittenNewsDTOPaginationActual =
                authorService.getPaginationAuthorIdWithAmountOfWrittenNews(authorIdWithAmountOfWrittenNewsDTOList,
                        countAllElements, page, size);
        assertEquals(authorIdWithAmountOfWrittenNewsDTOPaginationExpected, authorIdWithAmountOfWrittenNewsDTOPaginationActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldParams_when_foundSortField")
    void getOptionalSortField_when_foundSortField(String sortField, String sortFieldExpected) {
        String sortFieldActual = authorService.getOptionalSortField(sortField)
                .get()
                .name()
                .toLowerCase();
        assertEquals(sortFieldExpected, sortFieldActual);
    }

    static List<Arguments> providerSortFieldParams_when_foundSortField() {
        return List.of(
                Arguments.of("id", AuthorSortField.ID.name().toLowerCase()),
                Arguments.of("name", AuthorSortField.NAME.name().toLowerCase())
        );
    }

    @Test
    void getOptionalSortField_when_sortFieldIsNull() {
        Optional<AuthorSortField> optionalActual = authorService.getOptionalSortField(null);
        assertTrue(optionalActual.isEmpty());
    }

    @Test
    void getOptionalSortField_when_notFoundSortField() {
        Optional<AuthorSortField> optionalActual = authorService.getOptionalSortField("not_found_sort_field");
        assertTrue(optionalActual.isEmpty());
    }

    @AfterAll
    static void afterAll() {
        authorDTOTesting = null;
        authorDTOExpected = null;
        authorDTOActual = null;
    }
}