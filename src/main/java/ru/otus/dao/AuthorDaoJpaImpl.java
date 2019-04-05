package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.util.List;

@Repository
public class AuthorDaoJpaImpl extends GenericDaoJpaImpl<Author> implements AuthorDao {
    public AuthorDaoJpaImpl() {
        super(Author.class);
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select b from Author b"
                ,entityClass).getResultList();
    }

    @Override
    public Long getCount() {
        return em.createQuery("select count(b) from Author b"
                ,Long.class).getSingleResult();
    }
}
