package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        if (!books.isEmpty()){
            return new ResponseEntity<>(books,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.<ResponseEntity<?>>map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> removeBook(@PathVariable("id") long id){
        bookRepository.deleteById(id);
        return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/books"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editBook(@RequestBody Book requestBody){
        if (bookRepository.findById(requestBody.getId()).isPresent()){
            bookRepository.save(new Book(requestBody.getId()
                    , requestBody.getName()
                    , requestBody.getAuthor()
                    , requestBody.getGenre()));
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value="/books"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveBook(@RequestBody Book requestBody){
        bookRepository.save(new Book(requestBody.getId()
                , requestBody.getName()
                , requestBody.getAuthor()
                , requestBody.getGenre()));
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }

}
