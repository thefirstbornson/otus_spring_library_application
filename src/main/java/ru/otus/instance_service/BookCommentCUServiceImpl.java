package ru.otus.instance_service;

import org.springframework.stereotype.Service;
import ru.otus.dao.BookCommentDao;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.ioservice.IOService;

@Service
public class BookCommentCUServiceImpl implements BookCommentCUService {
    private final IOService ioservice;
    private final BookDao bookDao;
    private final BookCommentDao bookCommentDao;

    public BookCommentCUServiceImpl(IOService ioservice, BookDao bookDao, BookCommentDao bookCommentDao) {
        this.ioservice = ioservice;
        this.bookDao = bookDao;
        this.bookCommentDao = bookCommentDao;
    }

    @Override
    public BookComment create() {
        Book book = bookDao.findById(Long.parseLong(ioservice.userInput("Enter Book ID: ")));
        return new BookComment(
                ioservice.userInput(String.format("Leave your comment to %s: ", book.getName()))
                ,book
        );
    }

    @Override
    public BookComment update() {
        Book book = bookDao.findById(Long.parseLong(ioservice.userInput("Enter Book ID: ")));
        BookComment bookComment = bookCommentDao.findById(Long.parseLong(ioservice.userInput("Enter comment ID: ")));
        bookComment.setComment(ioservice.userInput(String.format("Change your comment to %s: ", book.getName())));
        return bookComment;
    }
}
