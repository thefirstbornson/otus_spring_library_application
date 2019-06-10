package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.GenreDto;
import ru.otus.repository.BookRepository;

@RestController
public class GenreController {
    private final BookRepository bookRepository;

    @Autowired
    public GenreController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/genres")
    public Flux<GenreDto> getAllGenres() {
        return bookRepository.findAll().map(book -> GenreDto.toDto(book.getGenre())).distinct();
    }
}
