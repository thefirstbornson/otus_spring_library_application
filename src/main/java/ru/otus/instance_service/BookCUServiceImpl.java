package ru.otus.instance_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Book;
import ru.otus.ioservice.IOService;

@Service
public class BookCUServiceImpl implements BookCUService {
    private final IOService ioservice;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookCUServiceImpl(IOService ioservice, AuthorDao authorDao, GenreDao genreDao) {
        this.ioservice = ioservice;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }


    @Override
    public Book create() {
        return new Book(
                ioservice.userInput("Enter title of the book: ")
                ,authorDao.findById(Long.parseLong(ioservice.userInput("Enter author's ID: ")))
                ,genreDao.findById(Long.parseLong(ioservice.userInput("Enter genre's ID: ")))
        );
    }

    @Override
    public Book update() {
        return new Book(
                Long.parseLong(ioservice.userInput("Enter ID:"))
                ,ioservice.userInput("Enter title of the book: ")
                ,authorDao.findById(Long.parseLong(ioservice.userInput("Enter author's ID: ")))
                ,genreDao.findById(Long.parseLong(ioservice.userInput("Enter genre's ID: ")))
        );
    }
}
