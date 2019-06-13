package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.GenreDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@RestController
public class GenreController {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
           }

    @GetMapping("/genres")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll();
    }
}
