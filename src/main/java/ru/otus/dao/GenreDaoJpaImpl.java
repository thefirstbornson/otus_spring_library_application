package ru.otus.dao;

import ru.otus.domain.Genre;

public class GenreDaoJpaImpl extends GenericDaoJpaImpl<Genre> implements GenreDao {
    protected GenreDaoJpaImpl() {
        super(Genre.class);
    }
}
