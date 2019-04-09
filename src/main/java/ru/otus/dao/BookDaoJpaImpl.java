package ru.otus.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.List;

@Repository
public class BookDaoJpaImpl extends GenericDaoJpaImpl<Book> implements BookDao{
    public BookDaoJpaImpl() {
        super(Book.class);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre"
                ,entityClass).getResultList();
    }

    @Override
    public Long getCount() {
        return em.createQuery("select count(b) from Book b",Long.class).getSingleResult();
    }
}
