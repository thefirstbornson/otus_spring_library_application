package ru.otus.repository;

import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.controller.dto.GenreDto;

public interface GenreRepository {
    Flux<GenreDto> findAll();
}
