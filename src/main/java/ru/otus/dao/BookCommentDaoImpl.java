package ru.otus.dao;

import org.springframework.stereotype.Repository;
import ru.otus.domain.BookComment;

@Repository
public class BookCommentDaoImpl extends GenericDaoJpaImpl<BookComment> implements BookCommentDao {
    public BookCommentDaoImpl() {
        super(BookComment.class);
    }
}
