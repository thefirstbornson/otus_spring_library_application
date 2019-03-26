package ru.otus.instance_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.ioservice.IOService;

@Service
public class AuthorCUServiceImpl implements AuthorCUService {
    private final IOService ioservice;

    @Autowired
    public AuthorCUServiceImpl(IOService ioservice) {
        this.ioservice = ioservice;
    }

    @Override
    public Author create() {
        return new Author(
                 ioservice.userInput("Enter author's first name: ")
                ,ioservice.userInput("Enter author's last name: ")
        );
    }

    @Override
    public Author update() throws NumberFormatException {
        return new Author(
                 Long.parseLong(ioservice.userInput("Enter ID:"))
                ,ioservice.userInput("Enter author's first name: ")
                ,ioservice.userInput("Enter author's last name: ")
        );
    }
}
