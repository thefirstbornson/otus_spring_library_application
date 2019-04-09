package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.BookComment;

import java.util.List;

@Repository
public class BookCommentDaoImpl extends GenericDaoJpaImpl<BookComment> implements BookCommentDao {
    public BookCommentDaoImpl() {
        super(BookComment.class);
    }

    @Override
    public List<BookComment> findAll() {
       return em.createQuery("select b from BookComment b inner join fetch b.book",entityClass).getResultList();
    }

    @Override
    public Long getCount() {
        return em.createQuery("select count (b) from BookComment b", Long.class).getSingleResult();
    }
}
