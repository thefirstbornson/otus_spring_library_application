package ru.otus.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import ru.otus.domain.Book;

@ExtendWith(SpringExtension.class)
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
public class AuthorRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    private @Autowired AuthorRepository authorRepository;

    @AfterEach
    void cleanDB (){
        bookRepository.deleteAll().block();
    }

    @Test
    @DisplayName("При подсчете количество уникальных авторов должно быть равно 1")
    void findAllAuthorTest() {
        bookRepository.save(new Book("9","Azazel"
                ,"Boris Akunin","Drama","Novel")).block();
        bookRepository.save(new Book("10","Aristonomia"
                ,"Boris Akunin","Drama","Novel")).block();
        bookRepository.save(new Book("11","Leviafan"
                ,"Boris Akunin","Drama","Novel")).block();

        StepVerifier.create(authorRepository.findAll().doOnNext(System.out::println))
                .expectNextCount(1)
                .verifyComplete();
    }
}
