package com.mjc.school.repository.impl.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface NewsRepository
        extends CRUDOperationRepository<News> {

    boolean deleteByAuthorId(long authorId);

    boolean deleteAllTagsFromNewsByNewsId(long newsId);

    List<News> findByTagName(String tagName, int page, int size);

    List<News> findByTagId(long tagId, int page, int size) ;

    List<News> findByAuthorName(String authorName, int page, int size);

    List<News> findByAuthorId(long authorId, int page, int size);

    boolean isExistsNewsWithTitle(String title);
}