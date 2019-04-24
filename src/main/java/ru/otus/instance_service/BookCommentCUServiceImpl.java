package ru.otus.instance_service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.exception.NoEntityException;
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
        Book book = null;
        try {
            book = bookRepository.findById(ioservice.userInput("Enter Book ID: ")).orElseThrow(NoEntityException::new);
            BookComment bookComment = new BookComment(
                    ioservice.userInput(String.format("Leave your comment to %s: ", book.getName()))
                    ,book
            );
            return bookComment;
        } catch (NoEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookComment update() {
        Book book = null;
        try {
            book = bookRepository.findById(ioservice.userInput("Enter Book ID: ")).orElseThrow(NoEntityException::new);
            BookComment bookComment = bookCommentRepository.findById(ioservice.userInput("Enter comment ID: "))
                    .orElseThrow(NoEntityException::new);
            bookComment.setComment(ioservice.userInput(String.format("Change your comment to %s: ", book.getName())));
            return bookComment;
        } catch (NoEntityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
