package com.mjc.school.service.author.impl;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.AuthorIdWithAmountOfWrittenNewsConverter;
import com.mjc.school.model.Author;
import com.mjc.school.model.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.author.impl.sort.AuthorSortField.getSortField;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LogManager.getLogger();
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final AuthorIdWithAmountOfWrittenNewsConverter authorIdWithAmountOfWrittenNewsConverter;
    private final PaginationService<AuthorDTO> authorPagination;
    private final PaginationService<AuthorIdWithAmountOfWrittenNewsDTO> amountOfWrittenNewsDTOPagination;

    @Transactional
    @Override
    public boolean create(AuthorDTO authorDTO) throws ServiceException {
        if (authorRepository.existsByName(authorDTO.getName())) {
            Author author = authorConverter.fromDTO(authorDTO);
            authorRepository.save(author);
            return true;
        } else {
            log.log(WARN, "Author with entered name '" + authorDTO.getName() + "' already exists");
            throw new ServiceException("tag_dto.name.not_valid.exists_tag_by_name");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
        return true;
    }

    @Transactional
    @Override
    public AuthorDTO update(AuthorDTO authorDTO) throws ServiceException {
        Author author = authorRepository.findById(authorDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found authors by ID: " + authorDTO.getId());
            return new ServiceException("service.exception.not_found_authors_by_id");
        });
        if (author.getName().equals(authorDTO.getName())) {
            return authorConverter.toDTO(author);
        } else {
            if (!authorRepository.existsByName(authorDTO.getName())) {
                author.setName(authorDTO.getName());
                authorRepository.update(author.getId(), author.getName());
                return authorConverter.toDTO(author);
            } else {
                log.log(WARN, "Author with entered name '" + authorDTO.getName() + "' already exists");
                throw new ServiceException("author_dto.name.not_valid.already_exists");
            }
        }
    }

    @Override
    public List<AuthorDTO> findAll(int page, int size, String sortField, String sortingType) throws ServiceException {
        Page<Author> authorPage = authorRepository.findAll(PageRequest.of(
                authorPagination.calcNumberFirstElement(page, size), size,
                Sort.by(fromOptionalString(sortingType).orElse(ASC),
                        getSortField(sortField).orElse(AuthorSortField.NAME.name().toLowerCase()))));
        if (!authorPage.isEmpty()) {
            return authorPage
                    .stream()
                    .map(authorConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found authors");
            throw new ServiceException("service.exception.not_found_authors");
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
    public long countAll() {
        return authorRepository.countAll();
    }

    @Override
    public AuthorDTO findById(long id) throws ServiceException {
        Author author = authorRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found author by ID: " + id);
            return new ServiceException("service.exception.not_found_author_by_id");
        });
        return authorConverter.toDTO(author);

    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName, int page, int size,
                                            String sortField, String sortingType) throws ServiceException {
        List<Author> authorsList = authorRepository.findByPartOfName(
                "%" + partOfName + "%",
                PageRequest.of(authorPagination.calcNumberFirstElement(page, size), size,
                        Sort.by(fromOptionalString(sortingType).orElse(ASC),
                                getSortField(sortField).orElse(AuthorSortField.NAME.name().toLowerCase()))));
        if (!authorsList.isEmpty()) {
            return authorsList.stream()
                    .map(authorConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found authors by part of name: " + partOfName);
            throw new ServiceException("service.exception.not_found_authors_by_part_of_name");
        }
    }

    @Override
    public long countAllByPartOfName(String partOfName) {
        return authorRepository.countAllByPartOfName("%" + partOfName + "%");
    }

    @Override
    public AuthorDTO findByNewsId(long newsId) throws ServiceException {
        Author author = authorRepository.findByNewsId(newsId).orElseThrow(() -> {
            log.log(WARN, "Not found authors by news ID: " + newsId);
            return new ServiceException("service.exception.not_found_authors_by_news_id");
        });
        return authorConverter.toDTO(author);
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO> findAllAuthorsIdWithAmountOfWrittenNews(
            int page, int size, String sortingType) throws ServiceException {
        String sortType = sortingType != null ?
                Sort.Direction.fromOptionalString(sortingType).orElse(DESC).name() :
                DESC.name();
        List<Author> authorList = null;
        if (sortType.equalsIgnoreCase("ASC")) {
            authorList = authorRepository.findAllAuthorsWithAmountWrittenNewsAsc(
                    PageRequest.of(authorPagination.calcNumberFirstElement(page, size), size));
        } else {
            authorList = authorRepository.findAllAuthorsWithAmountWrittenNewsDesc(
                    PageRequest.of(authorPagination.calcNumberFirstElement(page, size), size));
        }
        if (!authorList.isEmpty()) {
            return authorList.stream()
                    .map(author -> AuthorIdWithAmountOfWrittenNews.builder()
                            .authorId(author.getId())
                            .amountOfWrittenNews(author.getNews() != null
                                    ? author.getNews().size() : 0)
                            .build())
                    .map(authorIdWithAmountOfWrittenNewsConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found authors");
            throw new ServiceException("service.exception.not_found_authors");
        }
    }

    @Override
    public Pagination<AuthorDTO> getPagination(List<AuthorDTO> elementsOnPage, long countAllElements,
                                               int page, int size) {
        return authorPagination.getPagination(elementsOnPage, countAllElements, page, size);
    }

    @Override
    public Pagination<AuthorIdWithAmountOfWrittenNewsDTO> getPaginationAuthorIdWithAmountOfWrittenNews(
            List<AuthorIdWithAmountOfWrittenNewsDTO> elementsOnPage, long countAllElements, int page, int size) {
        return amountOfWrittenNewsDTOPagination.getPagination(elementsOnPage, countAllElements, page, size);
    }
}