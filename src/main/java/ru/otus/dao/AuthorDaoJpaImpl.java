package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

@Repository
public class AuthorDaoJpaImpl extends GenericDaoJpaImpl<Author> implements AuthorDao {
    public AuthorDaoJpaImpl() {
        super(Author.class);
    }
}
