package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

@Repository
public class GenreDaoJpaImpl extends GenericDaoJpaImpl<Genre> implements GenreDao {
    public GenreDaoJpaImpl() {
        super(Genre.class);
    }
}
