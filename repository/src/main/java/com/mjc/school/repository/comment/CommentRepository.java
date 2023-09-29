package com.mjc.school.repository.comment;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.CRUDOperationRepository;

import java.util.List;

public interface CommentRepository extends CRUDOperationRepository<Comment> {

    public List<Comment> findByNewsId(long newsId) throws RepositoryException;

    public boolean deleteByNewsId(long newsId) throws RepositoryException;
}