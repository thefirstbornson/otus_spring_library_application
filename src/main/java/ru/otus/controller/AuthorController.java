package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.repository.BookRepository;

@RestController
public class AuthorController {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorController( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return bookRepository.findAll().map(book -> AuthorDto.toDto(book.getAuthor())).distinct();
    }
}
