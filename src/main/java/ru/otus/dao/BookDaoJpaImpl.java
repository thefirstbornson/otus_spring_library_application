package ru.otus.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

@Repository
public class BookDaoJpaImpl extends GenericDaoJpaImpl<Book> implements BookDao{
    public BookDaoJpaImpl() {
        super(Book.class);
    }
}
