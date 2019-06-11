package ru.otus.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.controller.dto.GenreDto;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public GenreRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<GenreDto> findAll() {
        return Flux.from(reactiveMongoTemplate.getCollection("book")
                .distinct("genre",String.class)).map(GenreDto::toDto);
    }
}
