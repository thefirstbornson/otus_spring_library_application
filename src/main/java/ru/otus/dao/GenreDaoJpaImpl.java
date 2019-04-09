package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.BookComment;
import ru.otus.domain.Genre;

import java.util.List;

@Repository
public class GenreDaoJpaImpl extends GenericDaoJpaImpl<Genre> implements GenreDao {
    public GenreDaoJpaImpl() {
        super(Genre.class);
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select b from Genre b",entityClass).getResultList();
    }

    @Override
    public Long getCount() {
        return em.createQuery("select count (b) from Genre b", Long.class).getSingleResult();
    }
}
