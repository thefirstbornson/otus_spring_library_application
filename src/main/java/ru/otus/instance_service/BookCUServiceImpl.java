package ru.otus.instance_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.ioservice.IOService;

@Service
@RequiredArgsConstructor
public class BookCUServiceImpl implements BookCUService {
    private final IOService ioservice;
    
    @Override
    public Book create() {
        return new Book(
                ioservice.userInput("Enter title of the book: ")
                , ioservice.userInput("Enter author's name: ")
                , ioservice.userInput("Enter genre's name: ")
                , ioservice.userInput("Enter literary form: ")
        );
    }

    @Override
    public Book update() {
        return new Book(
                ioservice.userInput("Enter ID:")
                ,ioservice.userInput("Enter title of the book: ")
                , ioservice.userInput("Enter author's name: ")
                , ioservice.userInput("Enter genre's name: ")
                , ioservice.userInput("Enter literary form: ")
        );
    }
}
