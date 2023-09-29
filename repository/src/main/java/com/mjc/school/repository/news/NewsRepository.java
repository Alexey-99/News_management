package com.mjc.school.repository.news;

import com.mjc.school.entity.News;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface NewsRepository extends CRUDOperationRepository<News> {

    public boolean deleteByAuthorId(long authorId) throws RepositoryException;

    public boolean deleteAllTagsFromNewsByNewsId(long newsId) throws RepositoryException;

    public List<News> findByTagName(String tagName) throws RepositoryException;

    public List<News> findByTagId(long tagId) throws RepositoryException;

    public List<News> findByAuthorName(String authorName) throws RepositoryException;

    public List<News> findByAuthorId(long authorId) throws RepositoryException;

    public boolean isExistsNewsWithTitle(String title) throws RepositoryException;
}