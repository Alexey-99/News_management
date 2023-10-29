package com.mjc.school.service.author.impl;

import com.mjc.school.converter.impl.AuthorConverter;
import com.mjc.school.converter.impl.AuthorIdWithAmountOfWrittenNewsConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.Author;
import com.mjc.school.model.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.service.author.impl.sort.AuthorSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.AuthorIdWithAmountOfWrittenNewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final AuthorIdWithAmountOfWrittenNewsConverter authorIdWithAmountOfWrittenNewsConverter;
    private final PaginationService<AuthorDTO> authorPagination;
    private final PaginationService<AuthorIdWithAmountOfWrittenNewsDTO> amountOfWrittenNewsDTOPagination;

    @Transactional
    @Override
    public boolean create(AuthorDTO authorDTO) throws ServiceBadRequestParameterException {
        if (authorRepository.existsByName(authorDTO.getName())) {
            Author author = authorConverter.fromDTO(authorDTO);
            authorRepository.save(author);
            return true;
        } else {
            log.log(WARN, "Author with entered name '" + authorDTO.getName() + "' already exists");
            throw new ServiceBadRequestParameterException("tag_dto.name.not_valid.exists_tag_by_name");
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
    public AuthorDTO update(AuthorDTO authorDTO) throws ServiceBadRequestParameterException {
        Author author = authorRepository.findById(authorDTO.getId()).orElseThrow(() -> {
            log.log(WARN, "Not found authors by ID: " + authorDTO.getId());
            return new ServiceBadRequestParameterException("service.exception.not_found_authors_by_id");
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
                throw new ServiceBadRequestParameterException("author_dto.name.not_valid.already_exists");
            }
        }
    }

    @Override
    public List<AuthorDTO> findAll(int page, int size, String sortField, String sortingType) throws ServiceNoContentException {
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
            throw new ServiceNoContentException("service.exception.not_found_authors");
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
    public AuthorDTO findById(long id) throws ServiceNoContentException {
        Author author = authorRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found author by ID: " + id);
            return new ServiceNoContentException("service.exception.not_found_author_by_id");
        });
        return authorConverter.toDTO(author);

    }

    @Override
    public List<AuthorDTO> findByPartOfName(String partOfName,
                                            int page, int size,
                                            String sortField, String sortingType) throws ServiceNoContentException {
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
            throw new ServiceNoContentException("service.exception.not_found_authors_by_part_of_name");
        }
    }

    @Override
    public long countAllByPartOfName(String partOfName) {
        return authorRepository.countAllByPartOfName("%" + partOfName + "%");
    }

    @Override
    public AuthorDTO findByNewsId(long newsId) throws ServiceNoContentException {
        Author author = authorRepository.findByNewsId(newsId).orElseThrow(() -> {
            log.log(WARN, "Not found authors by news ID: " + newsId);
            return new ServiceNoContentException("service.exception.not_found_authors_by_news_id");
        });
        return authorConverter.toDTO(author);
    }

    @Override
    public List<AuthorIdWithAmountOfWrittenNewsDTO> findAllAuthorsIdWithAmountOfWrittenNews(int page, int size,
                                                                                            String sortingType) throws ServiceNoContentException {
        String sortType = sortingType != null ?
                Sort.Direction.fromOptionalString(sortingType).orElse(DESC).name() :
                DESC.name();
        List<Author> authorList;
        if (sortType.equalsIgnoreCase(ASC.name())) {
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
            throw new ServiceNoContentException("service.exception.not_found_authors");
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