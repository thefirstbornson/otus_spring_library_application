package ru.otus.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.controller.dto.AuthorDto;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public AuthorRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<AuthorDto> findAll() {
        return Flux.from(reactiveMongoTemplate.getCollection("book")
                .distinct("author",String.class)).map(AuthorDto::toDto);
    }
}
