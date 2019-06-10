package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @GetMapping("/books")
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Mono<ResponseEntity<Book>> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/books/{id}")
    public Mono<ResponseEntity<Void>> removeBook(@PathVariable("id") String id){
        return bookRepository.findById(id)
                .flatMap(existingBook ->
                        bookRepository.delete(existingBook)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value="/books/{id}")
    public Mono<ResponseEntity<Book>> editBook(@PathVariable("id") String id, @RequestBody Book requestBody){
        return bookRepository.findById(id).flatMap(book -> {
            book.setName(requestBody.getName());
            book.setAuthor(requestBody.getAuthor());
            book.setGenre(requestBody.getGenre());
            return bookRepository.save(book);
        })
                .map(updateBook -> new ResponseEntity<>(updateBook, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value="/books")
    public Mono<Book> saveBook(@RequestBody Book requestBody){
        return bookRepository.save(requestBody);
    }

}