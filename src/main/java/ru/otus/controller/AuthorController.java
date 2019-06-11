package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;


    @Autowired
    public AuthorController( AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll();
    }
}
