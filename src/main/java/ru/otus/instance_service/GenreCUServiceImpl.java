package ru.otus.instance_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;
import ru.otus.ioservice.IOService;

@Service
public class GenreCUServiceImpl implements GenreCUService {
    private final IOService ioservice;

    @Autowired
    public GenreCUServiceImpl(IOService ioservice) {
        this.ioservice = ioservice;
    }
    @Override
    public Genre create() {
        return new Genre(ioservice.userInput("Enter genre name: "));
    }

    @Override
    public Genre update() {
        return new Genre(
                ioservice.userInput("Enter ID:")
                ,ioservice.userInput("Enter genre name: ")
        );
    }
}
