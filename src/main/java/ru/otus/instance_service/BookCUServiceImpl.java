package ru.otus.instance_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otus.domain.Book;
import ru.otus.ioservice.IOService;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.GenreRepository;

@Service
public class BookCUServiceImpl implements BookCUService {
    private final IOService ioservice;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookCUServiceImpl(IOService ioservice, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.ioservice = ioservice;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }


    @Override
    public Book create() {
        return new Book(
                ioservice.userInput("Enter title of the book: ")
                , authorRepository.findById(Long.parseLong(ioservice.userInput("Enter author's ID: "))).get()
                , genreRepository.findById(Long.parseLong(ioservice.userInput("Enter genre's ID: "))).get()
        );
    }

    @Override
    public Book update() {
        return new Book(
                Long.parseLong(ioservice.userInput("Enter ID:"))
                ,ioservice.userInput("Enter title of the book: ")
                , authorRepository.findById(Long.parseLong(ioservice.userInput("Enter author's ID: "))).get()
                , genreRepository.findById(Long.parseLong(ioservice.userInput("Enter genre's ID: "))).get()
        );
    }
}
