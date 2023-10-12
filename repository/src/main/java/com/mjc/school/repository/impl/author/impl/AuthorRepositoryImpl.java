package com.mjc.school.repository.impl.author.impl;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.News;
import com.mjc.school.repository.impl.CRUDOperationRepositoryImpl;
import com.mjc.school.repository.impl.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class AuthorRepositoryImpl
        extends CRUDOperationRepositoryImpl<Author>
        implements AuthorRepository {
    private final EntityManager entityManager;

    @Autowired
    public AuthorRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public boolean create(Author author) {
        entityManager.persist(author);
        return findById(author.getId()) != null;
    }

    @Override
    public Author findByNewsId(long newsId) {
        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> authorCriteriaQuery =
                criteriaBuilder.createQuery(Author.class);
        Root<Author> authorRoot =
                authorCriteriaQuery.from(Author.class);
        Join<Author, News> authorNewsJoin =
                authorRoot.join("news", JoinType.INNER);
        authorCriteriaQuery.multiselect(authorNewsJoin)
                .where(criteriaBuilder.equal(
                        authorNewsJoin.get("news.id"), newsId));
        return entityManager.createQuery(authorCriteriaQuery)
                .getSingleResult();
    }

    @Override
    public boolean isNotExistsAuthorWithName(String name) {
        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> authorCriteriaQuery =
                criteriaBuilder.createQuery(Author.class);
        Root<Author> authorRoot =
                authorCriteriaQuery.from(Author.class);
        authorCriteriaQuery.where(
                criteriaBuilder.equal(
                        authorRoot.get("name"), name));
        return entityManager.createQuery(authorCriteriaQuery)
                .getResultList()
                .isEmpty();
    }

    @Override
    protected Class<Author> getEntityClass() {
        return Author.class;
    }
}