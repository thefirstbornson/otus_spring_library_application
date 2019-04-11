package ru.otus.instance_service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.ioservice.IOService;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;

@Service
public class BookCommentCUServiceImpl implements BookCommentCUService {
    private final IOService ioservice;
    private final BookRepository bookRepository;
    private final BookCommentRepository bookCommentRepository;

    public BookCommentCUServiceImpl(IOService ioservice, BookRepository bookRepository
            , BookCommentRepository bookCommentRepository) {
        this.ioservice = ioservice;
        this.bookRepository = bookRepository;
        this.bookCommentRepository = bookCommentRepository;
    }

    @Override
    public BookComment create() {
        Book book = bookRepository.findById(Long.parseLong(ioservice.userInput("Enter Book ID: "))).get();
        return new BookComment(
                ioservice.userInput(String.format("Leave your comment to %s: ", book.getName()))
                ,book
        );
    }

    @Override
    public BookComment update() {
        Book book = bookRepository.findById(Long.parseLong(ioservice.userInput("Enter Book ID: "))).get();
        BookComment bookComment = bookCommentRepository.findById(Long.parseLong(ioservice.userInput("Enter comment ID: "))).get();
        bookComment.setComment(ioservice.userInput(String.format("Change your comment to %s: ", book.getName())));
        return bookComment;
    }
}
