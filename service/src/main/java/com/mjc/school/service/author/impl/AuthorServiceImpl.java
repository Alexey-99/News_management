package com.mjc.school.service.author.impl;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.AuthorIdWithAmountOfWrittenNewsConverter;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_AUTHOR_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_PART_OF_NAME;
import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LogManager.getLogger();
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final AuthorIdWithAmountOfWrittenNewsConverter
            authorIdWithAmountOfWrittenNewsConverter;
    private final PaginationService authorPagination;

    @Transactional
    @Override
    public boolean create(AuthorDTO authorDTO) {
        Author author = authorConverter.fromDTO(authorDTO);
        authorRepository.save(author);
        return authorRepository.existsById(author.getId());
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        authorRepository.deleteById(id);
        return !authorRepository.existsById(id);
    }

    @Transactional
    @Override
    public AuthorDTO update(AuthorDTO authorDTO) throws ServiceException {
        if (authorRepository.existsById(authorDTO.getId())) {
            authorRepository.update(authorDTO.getId(), authorDTO.getName());
            return authorConverter.toDTO(
                    authorRepository.findById(authorDTO.getId()).get());
        } else {
            log.log(WARN, "Not found object with this ID: " + authorDTO.getId());
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<AuthorDTO> findAll(int page, int size) throws ServiceException {
        Page<Author> authorPage = authorRepository.findAll(
                PageRequest.of(
                        authorPagination.calcNumberFirstElement(page, size),
                        size));
        if (!authorPage.isEmpty()) {
            return authorPage
                    .stream()
                    .map(authorConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorConverter::toDTO)
                .toList();

    }

    @Override
    public AuthorDTO findById(long id) throws ServiceException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return authorConverter.toDTO(author.get());
        } else {
            log.log(WARN, "Not found object with this ID: " + id);
            throw new ServiceException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName, int page, int size)
            throws ServiceException {
        String patternPartOfName = "%".concat(partOfName).concat("%");
        List<Author> authors = authorRepository.findByPartOfName(
                patternPartOfName,
                authorPagination.calcNumberFirstElement(page, size),
                size);
        if (!authors.isEmpty()) {
            return authors.stream()
                    .map(authorConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found object with this part of name: " + partOfName);
            throw new ServiceException(NO_ENTITY_WITH_PART_OF_NAME);
        }
    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName) {
        String patternPartOfName = "%".concat(partOfName).concat("%");
        return authorRepository.findByPartOfName(patternPartOfName)
                .stream()
                .map(authorConverter::toDTO)
                .toList();
    }

    @Override
    public AuthorDTO findByNewsId(long newsId)
            throws ServiceException {
        Author author = authorRepository.findByNewsId(newsId);
        if (author != null) {
            return authorConverter.toDTO(author);
        } else {
            log.log(WARN, "Not found objects with author news ID: " + newsId);
            throw new ServiceException(NO_ENTITY_WITH_AUTHOR_NEWS_ID);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews(int page, int size)
            throws ServiceException {
        Page<Author> authorPage = authorRepository.findAll(
                PageRequest.of(
                        authorPagination.calcNumberFirstElement(page, size),
                        size));
        if (!authorPage.isEmpty()) {
            return authorPage.stream()
                    .map(author ->
                            AuthorIdWithAmountOfWrittenNews
                                    .builder()
                                    .authorId(author.getId())
                                    .amountOfWrittenNews(
                                            author.getNews() != null
                                                    ? author.getNews().size()
                                                    : 0)
                                    .build())
                    .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    selectAllAuthorsIdWithAmountOfWrittenNews() {
        return authorRepository.findAll()
                .stream()
                .map(author ->
                        AuthorIdWithAmountOfWrittenNews
                                .builder()
                                .authorId(author.getId())
                                .amountOfWrittenNews(
                                        author.getNews() != null
                                                ? author.getNews().size()
                                                : 0)
                                .build())
                .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                .toList();
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc(int page, int size)
            throws ServiceException {
        List<AuthorIdWithAmountOfWrittenNews> authorIdWithAmountOfWrittenNewsList =
                new LinkedList<>(authorRepository.sortAllAuthorsWithAmountWrittenNewsDesc(
                                authorPagination.calcNumberFirstElement(page, size),
                                size)
                        .stream()
                        .map(author ->
                                AuthorIdWithAmountOfWrittenNews
                                        .builder()
                                        .authorId(author.getId())
                                        .amountOfWrittenNews(
                                                author.getNews() != null
                                                        ? author.getNews().size()
                                                        : 0)
                                        .build())
                        .toList());
        if (!authorIdWithAmountOfWrittenNewsList.isEmpty()) {
            return authorIdWithAmountOfWrittenNewsList.stream()
                    .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found objects");
            throw new ServiceException(NO_ENTITY);
        }
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO>
    sortAllAuthorsIdWithAmountOfWrittenNewsDesc() {
        return new LinkedList<>(authorRepository.sortAllAuthorsWithAmountWrittenNewsDesc()
                .stream()
                .map(author ->
                        AuthorIdWithAmountOfWrittenNews
                                .builder()
                                .authorId(author.getId())
                                .amountOfWrittenNews(
                                        author.getNews() != null
                                                ? author.getNews().size()
                                                : 0)
                                .build())
                .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                .toList());
    }

    @Override
    public Pagination<AuthorDTO> getPagination(
            List<AuthorDTO> elementsOnPage,
            List<AuthorDTO> allElementsList,
            int page, int size) {
        return Pagination
                .<AuthorDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .numberPage(page)
                .maxNumberPage(
                        authorPagination.calcMaxNumberPage(
                                allElementsList.size(),
                                size))
                .build();
    }

    @Override
    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO>
    getPaginationAuthorIdWithAmountOfWrittenNews(
            List<AuthorIdWithAmountOfWrittenNewsDTO> elementsOnPage,
            List<AuthorIdWithAmountOfWrittenNewsDTO> allElementsList,
            int page, int size) {
        return Pagination
                .<AuthorIdWithAmountOfWrittenNewsDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .numberPage(page)
                .maxNumberPage(
                        authorPagination.calcMaxNumberPage(
                                allElementsList.size(),
                                size))
                .build();
    }
}