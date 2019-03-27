package ru.otus.dao;

import ru.otus.domain.Book;

public class BookDaoJpaImpl extends GenericDaoJpaImpl<Book> implements BookDao{
    protected BookDaoJpaImpl() {
        super(Book.class);
    }
}
