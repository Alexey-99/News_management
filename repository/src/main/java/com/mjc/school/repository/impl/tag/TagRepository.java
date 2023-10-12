package com.mjc.school.repository.impl.tag;

import com.mjc.school.entity.Tag;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface TagRepository extends CRUDOperationRepository<Tag> {
    boolean addToNews(long tagId, long newsId) ;

    boolean removeFromNews(long tagId, long newsId);

    boolean deleteAllTagsFromNewsByNewsId(long tagId);

    List<Tag> findByNewsId(long newsId, int page, int size);
    List<Tag> findByNewsId(long newsId);

    boolean isExistsTagWithName(String name) ;
}