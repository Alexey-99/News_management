package com.mjc.school.service;

import com.mjc.school.converter.impl.TagConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.News;
import com.mjc.school.model.NewsTag;
import com.mjc.school.model.Tag;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.tag.impl.TagServiceImpl;
import com.mjc.school.service.tag.impl.sort.TagSortField;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.TagDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsTagRepository newsTagRepository;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private PaginationService paginationService;

    @Test
    void create_when_tagNotExistsByName() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name_test").build();
        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(true);
        when(tagConverter.fromDTO(tagDTOTesting))
                .thenReturn(Tag.builder().id(1).name("tag_name_test").build());
        assertTrue(tagService.create(tagDTOTesting));
    }

    @Test
    void create_when_tagExistsByName() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name_test").build();
        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(false);
        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.create(tagDTOTesting));
        assertEquals("tag_dto.name.not_valid.exists_tag_by_name",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_notFoundTagById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.not_found_tag_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_notFoundNewsById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId))
                .thenReturn(Optional.of(Tag.builder().id(tagId).build()));
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.not_found_news_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_foundNewsById_and_tagPresentInNews() {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsTag newsTag = NewsTag.builder().id(1).tag(tagFromDB).news(newsFromDB).build();
        tagFromDB.setNews(List.of(newsTag));
        newsFromDB.setTags(List.of(newsTag));

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.addToNews(tagId, newsId));
        assertEquals("service.exception.exists_tag_in_news",
                exceptionActual.getMessage());
    }

    @Test
    void addToNews_when_foundTagById_and_foundNewsById_and_tagNotPresentInNews() throws ServiceBadRequestParameterException {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).news(List.of()).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).tags(List.of()).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        when(newsTagRepository.save(any(NewsTag.class))).thenReturn(null);

        boolean actualResult = tagService.addToNews(tagId, newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteFromNews_when_notFoundTagById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.deleteFromNews(tagId, newsId));
        assertEquals("service.exception.not_found_tag_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void deleteFromNews_when_foundTagById_and_notFoundNewsById() {
        long tagId = 1;
        long newsId = 2;

        when(tagRepository.findById(tagId))
                .thenReturn(Optional.of(Tag.builder().id(tagId).build()));
        when(newsRepository.findById(newsId))
                .thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.deleteFromNews(tagId, newsId));
        assertEquals("service.exception.not_found_news_by_id",
                exceptionActual.getMessage());
    }

    @Test
    void deleteFromNews_when_foundTagById_and_foundNewsById_and_tagPresentInNews() throws ServiceBadRequestParameterException {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsTag newsTag = NewsTag.builder().id(1).tag(tagFromDB).news(newsFromDB).build();
        tagFromDB.setNews(List.of(newsTag));
        newsFromDB.setTags(List.of(newsTag));

        boolean actualResult = tagService.deleteFromNews(tagId, newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteFromNews_when_foundTagById_and_foundNewsById_and_tagNotPresentInNews() throws ServiceBadRequestParameterException {
        long tagId = 1;
        long newsId = 2;

        Tag tagFromDB = Tag.builder().id(tagId).news(List.of()).build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        News newsFromDB = News.builder().id(newsId).tags(List.of()).build();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(newsFromDB));

        NewsTag newsTag = NewsTag.builder().id(1)
                .tag(Tag.builder().id(5).build())
                .news(News.builder().id(6).build()).build();
        tagFromDB.setNews(List.of(newsTag));
        newsFromDB.setTags(List.of(newsTag));

        boolean actualResult = tagService.deleteFromNews(tagId, newsId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_tagExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(true);
        boolean actualResult = tagService.deleteById(tagId);
        assertTrue(actualResult);
    }

    @Test
    void deleteById_when_tagNotExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(false);
        boolean actualResult = tagService.deleteById(tagId);
        assertTrue(actualResult);
    }

    @Test
    void deleteFromAllNews_when_tagExistsById() throws ServiceBadRequestParameterException {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(true);
        boolean actualResult = tagService.deleteFromAllNews(tagId);
        assertTrue(actualResult);
    }

    @Test
    void deleteFromAllNews_when_tagNotExistsById() {
        long tagId = 1;
        when(tagRepository.existsById(tagId)).thenReturn(false);
        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.deleteFromAllNews(tagId));
        assertEquals("service.exception.not_found_tag_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_notFoundTagById() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).build();

        when(tagRepository.findById(tagDTOTesting.getId())).thenReturn(Optional.empty());

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.update(tagDTOTesting));

        assertEquals("service.exception.not_found_tag_by_id", exceptionActual.getMessage());
    }

    @Test
    void update_when_foundTagById_and_equalTagNames() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("tag_name").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        TagDTO tagDTOActual = tagService.update(tagDTOTesting);

        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void update_when_foundTagById_and_notEqualTagNames_and_tagNotExistsByName() throws ServiceBadRequestParameterException {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name_other").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("tag_name_other").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(true);

        TagDTO tagDTOActual = tagService.update(tagDTOTesting);

        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void update_when_foundTagById_and_notEqualTagNames_and_tagExistsByName() {
        TagDTO tagDTOTesting = TagDTO.builder().id(1).name("tag_name").build();

        Tag tagFromDB = Tag.builder().id(1).name("tag_name_other").build();
        when(tagRepository.findById(tagDTOTesting.getId()))
                .thenReturn(Optional.of(tagFromDB));

        when(tagRepository.notExistsByName(tagDTOTesting.getName())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> tagService.update(tagDTOTesting));

        assertEquals("tag_dto.name.not_valid.exists_tag_by_name", exceptionActual.getMessage());
    }

    @Test
    void findAllWithPages_when_foundTags() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());
        when(tagRepository.findAllList(PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Tag.builder().id(1).name("A_tag_name").build(),
                        Tag.builder().id(3).name("C_tag_name").build(),
                        Tag.builder().id(2).name("B_tag_name").build()));

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll(page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void findAllWithPages_when_notFoundTags() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);
        when(tagRepository.findAllList(PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findAll(page, size, sortField, sortType));
    }

    @Test
    void findAll_when_foundTags() {
        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name").build());
        when(tagConverter.toDTO(Tag.builder().id(2).name("B_tag_name").build()))
                .thenReturn(TagDTO.builder().id(2).name("B_tag_name").build());
        when(tagRepository.findAll()).thenReturn(List.of(
                Tag.builder().id(1).name("A_tag_name").build(),
                Tag.builder().id(3).name("C_tag_name").build(),
                Tag.builder().id(2).name("B_tag_name").build()));

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build());

        List<TagDTO> tagDTOListActual = tagService.findAll();
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void findAll_when_notFoundTags() {
        when(tagRepository.findAll()).thenReturn(List.of());

        List<TagDTO> tagDTOListExpected = List.of();
        List<TagDTO> tagDTOListActual = tagService.findAll();

        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void countAll() {
        long countAllTags = 3L;

        when(tagRepository.countAll()).thenReturn(countAllTags);

        long countAllActual = tagService.countAll();
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }

    @Test
    void findById_when_foundTagById() throws ServiceNoContentException {
        long tagId = 1;

        Tag tagFromDB = Tag.builder().id(1).name("A_tag_name").build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tagFromDB));

        TagDTO tagDTOExpected = TagDTO.builder().id(1).name("A_tag_name").build();
        when(tagConverter.toDTO(tagFromDB)).thenReturn(tagDTOExpected);

        TagDTO tagDTOActual = tagService.findById(tagId);
        assertEquals(tagDTOExpected, tagDTOActual);
    }

    @Test
    void findById_when_notFoundTagById() {
        long tagId = 1;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());
        assertThrows(ServiceNoContentException.class,
                () -> tagService.findById(tagId));

    }

    @Test
    void findByPartOfName_when_foundTags() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "name_choose";

        when(tagConverter.toDTO(Tag.builder().id(1).name("A_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(1).name("A_tag_name_choose").build());
        when(tagConverter.toDTO(Tag.builder().id(3).name("C_tag_name_choose").build()))
                .thenReturn(TagDTO.builder().id(3).name("C_tag_name_choose").build());

        when(tagRepository.findByPartOfName("%" + partOfName + "%",
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Tag.builder().id(1).name("A_tag_name_choose").build(),
                        Tag.builder().id(3).name("C_tag_name_choose").build()));

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder().id(1).name("A_tag_name_choose").build(),
                TagDTO.builder().id(3).name("C_tag_name_choose").build());

        List<TagDTO> tagDTOListActual = tagService.findByPartOfName(partOfName, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void findByPartOfName_when_notFoundTags() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        String partOfName = "name_choose";

        when(tagRepository.findByPartOfName("%" + partOfName + "%",
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findByPartOfName(partOfName, page, size, sortField, sortType));
    }

    @Test
    void countAllByPartOfName() {
        String partOfName = "name_choose";
        long countAllByPartOfName = 2;

        when(tagRepository.countAllByPartOfName("%" + partOfName + "%"))
                .thenReturn(countAllByPartOfName);

        long countAllByPartOfNameActual = tagService.countAllByPartOfName(partOfName);
        long countAllByPartOfNameExpected = 2;

        assertEquals(countAllByPartOfNameExpected, countAllByPartOfNameActual);
    }

    @Test
    void findByNewsId_when_notFoundTags() {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        long newsId = 1;

        when(tagRepository.findByNewsId(newsId,
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> tagService.findByNewsId(newsId, page, size, sortField, sortType));
    }

    @Test
    void findByNewsId_when_foundTags() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        int numberFirstElement = 0;
        String sortType = "ASC";
        String sortField = "name";
        long newsId = 1;

        News news = News.builder().id(newsId).build();

        when(tagConverter.toDTO(Tag.builder()
                .id(1)
                .name("A_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build());
        when(tagConverter.toDTO(Tag.builder()
                .id(3)
                .name("C_tag_name")
                .news(List.of(NewsTag.builder().news(news).build()))
                .build()))
                .thenReturn(TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        when(tagRepository.findByNewsId(newsId,
                PageRequest.of(numberFirstElement, size, Sort.by(ASC, sortField))))
                .thenReturn(List.of(
                        Tag.builder()
                                .id(1)
                                .name("A_tag_name")
                                .news(List.of(NewsTag.builder()
                                        .news(news)
                                        .build()))
                                .build(),
                        Tag.builder()
                                .id(3)
                                .name("C_tag_name")
                                .news(List.of(NewsTag.builder()
                                        .news(news)
                                        .build()))
                                .build()));

        List<TagDTO> tagDTOListExpected = List.of(
                TagDTO.builder()
                        .id(1)
                        .name("A_tag_name")
                        .countNews(1)
                        .build(),
                TagDTO.builder()
                        .id(3)
                        .name("C_tag_name")
                        .countNews(1)
                        .build());

        List<TagDTO> tagDTOListActual = tagService.findByNewsId(newsId, page, size, sortField, sortType);
        assertEquals(tagDTOListExpected, tagDTOListActual);
    }

    @Test
    void countAllByNewsId() {
        long newsId = 1;
        long countAllByNewsId = 2;

        when(tagRepository.countAllByNewsId(newsId)).thenReturn(countAllByNewsId);

        long countAllByNewsIdActual = tagService.countAllByNewsId(newsId);
        long countAllByNewsIdExpected = 2;

        assertEquals(countAllByNewsIdExpected, countAllByNewsIdActual);
    }

    @Test
    void getPagination() {
        List<TagDTO> tagDTOList = List.of(
                TagDTO.builder().id(1).name("A_tag_name").build(),
                TagDTO.builder().id(3).name("C_tag_name").build(),
                TagDTO.builder().id(2).name("B_tag_name").build(),
                TagDTO.builder().id(4).name("D_tag_name").build(),
                TagDTO.builder().id(5).name("Z_tag_name").build());
        long countAllElements = 10;
        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 2;

        when(paginationService.calcMaxNumberPage(countAllElements, size))
                .thenReturn(maxNumberPageExpected);

        Pagination<TagDTO> tagDTOPaginationExpected = Pagination.<TagDTO>builder()
                .entity(tagDTOList)
                .size(size)
                .numberPage(page)
                .countAllEntity(countAllElements)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        Pagination<TagDTO> tagDTOPaginationActual =
                tagService.getPagination(tagDTOList, countAllElements, page, size);
        assertEquals(tagDTOPaginationExpected, tagDTOPaginationActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldParams_when_foundSortField")
    void getOptionalSortField_when_foundSortField(String sortField, String sortFieldExpected) {
        String sortFieldActual = tagService.getOptionalSortField(sortField)
                .get()
                .name()
                .toLowerCase();
        assertEquals(sortFieldExpected, sortFieldActual);
    }

    static List<Arguments> providerSortFieldParams_when_foundSortField() {
        return List.of(
                Arguments.of("id", TagSortField.ID.name().toLowerCase()),
                Arguments.of("name", TagSortField.NAME.name().toLowerCase())
        );
    }

    @Test
    void getOptionalSortField_when_sortFieldIsNull() {
        Optional<TagSortField> optionalActual = tagService.getOptionalSortField(null);
        assertTrue(optionalActual.isEmpty());
    }

    @Test
    void getOptionalSortField_when_notFoundSortField() {
        Optional<TagSortField> optionalActual = tagService.getOptionalSortField("not_found_sort_field");
        assertTrue(optionalActual.isEmpty());
    }
}