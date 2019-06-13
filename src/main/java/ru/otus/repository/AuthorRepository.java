package ru.otus.repository;

import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;

public interface AuthorRepository {
    Flux<AuthorDto> findAll();
}
