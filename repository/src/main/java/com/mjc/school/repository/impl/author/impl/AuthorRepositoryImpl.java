package com.mjc.school.repository.impl.author.impl;

import com.mjc.school.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl
        //extends CRUDOperationRepositoryImpl<Author>
       /* implements AuthorRepository*/ {
    private final EntityManager entityManager;

//    //@Override
//    @Transactional
//    public boolean create(Author author) {
//        entityManager.persist(author);
//        return findById(author.getId()) != null;
//    }

//    //@Override
//    public Author findByNewsId(long newsId) {
//        CriteriaBuilder criteriaBuilder =
//                entityManager.getCriteriaBuilder();
//        CriteriaQuery<Author> authorCriteriaQuery =
//                criteriaBuilder.createQuery(Author.class);
//        Root<Author> authorRoot =
//                authorCriteriaQuery.from(Author.class);
//        Join<Author, News> authorNewsJoin =
//                authorRoot.join("news", JoinType.INNER);
//        authorCriteriaQuery.multiselect(authorNewsJoin)
//                .where(criteriaBuilder.equal(
//                        authorNewsJoin.get("news.id"), newsId));
//        return entityManager.createQuery(authorCriteriaQuery)
//                .getSingleResult();
//    }

   // @Override
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

    //@Override
    protected Class<Author> getEntityClass() {
        return Author.class;
    }
}