package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book,String> {

    Flux<Book> findAllByAuthor(String author);
    Mono<Book> findFirstByGenre(Mono<String> genre);

}
