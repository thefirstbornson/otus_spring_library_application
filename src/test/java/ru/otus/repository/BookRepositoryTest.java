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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.domain.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class})
@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
 class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @AfterEach
    void cleanDB (){
        bookRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Найти сохраненную книгу по автору")
    void findAllByAuthorTest() {
        bookRepository.save(new Book("9","Aristonomia"
                ,"Boris Akunin","Drama","Novel")).block();
        Flux<Book> bookFlux = bookRepository.findAllByAuthor("Boris Akunin");

        StepVerifier
                .create(bookFlux)
                .assertNext(book -> {
                    assertEquals("Boris Akunin", book.getAuthor());
                    assertEquals("Aristonomia" , book.getName());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Найти сохраненную книгу по жанру")
    void findFirstByGenreTest() {
        bookRepository.save(new Book("9","Aristonomia"
                ,"Boris Akunin","Drama","Novel")).block();
        Mono<Book> bookFlux = bookRepository.findFirstByGenre(Mono.just("Drama"));

        StepVerifier
                .create(bookFlux)
                .assertNext(book -> {
                    assertEquals("Boris Akunin", book.getAuthor());
                    assertEquals("Aristonomia" , book.getName());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }


    @Test
    @DisplayName("При подсчете количества книг их общее число должно быть 3")
    void findAllTest() {
        bookRepository.save(new Book("9","Azazel"
                ,"Boris Akunin","Drama","Novel")).block();
        bookRepository.save(new Book("10","Aristonomia"
                ,"Boris Akunin","Drama","Novel")).block();
        bookRepository.save(new Book("11","Leviafan"
                ,"Boris Akunin","Drama","Novel")).block();
        StepVerifier.create(bookRepository.findAll().doOnNext(System.out::println))
                .expectNextCount(3)
                .verifyComplete();
    }
}
