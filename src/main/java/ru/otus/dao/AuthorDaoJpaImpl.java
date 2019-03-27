package ru.otus.dao;

import ru.otus.domain.Author;

public class AuthorDaoJpaImpl extends GenericDaoJpaImpl<Author> implements AuthorDao {
    protected AuthorDaoJpaImpl() {
        super(Author.class);
    }
}
