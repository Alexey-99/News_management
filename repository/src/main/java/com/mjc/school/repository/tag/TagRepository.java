package com.mjc.school.repository.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface TagRepository extends CRUDOperationRepository<Tag> {
    public boolean addToNews(long tagId, long newsId) throws RepositoryException;

    public boolean removeFromNews(long tagId, long newsId) throws RepositoryException;

    public boolean deleteAllTagsFromNewsByNewsId(long tagId) throws RepositoryException;

    public List<Tag> findByNewsId(long newsId) throws RepositoryException;

    public boolean isExistsTagWithName(String name) throws RepositoryException;
}