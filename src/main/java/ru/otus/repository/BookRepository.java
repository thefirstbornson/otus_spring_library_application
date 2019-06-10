package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book,String> {

}
