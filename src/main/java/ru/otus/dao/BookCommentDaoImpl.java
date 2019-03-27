package ru.otus.dao;

import ru.otus.domain.BookComment;

public class BookCommentDaoImpl extends GenericDaoJpaImpl<BookComment> implements BookCommentDao {
    protected BookCommentDaoImpl() {
        super(BookComment.class);
    }
}
