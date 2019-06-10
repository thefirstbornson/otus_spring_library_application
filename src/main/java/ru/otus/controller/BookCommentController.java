package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.controller.dto.BookCommentDto;
import ru.otus.domain.BookComment;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;

import java.util.Objects;

@RestController
public class BookCommentController {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookCommentController(BookCommentRepository bookCommentRepository, BookRepository bookRepository) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/bookcomments")
    public Flux<BookCommentDto> getAllBookComments() {
        return bookCommentRepository.findAll().map(bookComment ->
                BookCommentDto.toDto(bookComment.getId(), bookComment.getComment()
                        ,Objects.requireNonNull(bookRepository.findById(bookComment.getBookid())
                                .block())
                                .getName()
        ));
    }

    @GetMapping("/bookcomments/{id}")
    public Mono<ResponseEntity<BookComment>> getBookComment(@PathVariable("id") String id) {
        return bookCommentRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/bookcomments/{id}")
    public Mono<ResponseEntity<Void>> removeBookComment(@PathVariable("id") String id){
        return bookCommentRepository.findById(id)
                .flatMap(existingComment ->
                        bookCommentRepository.delete(existingComment)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value="/bookcomments/{id}")
    public Mono<ResponseEntity<BookComment>> editBookComment(@PathVariable("id") String id, @RequestBody BookComment requestBody){
        return bookCommentRepository.findById(id).flatMap(comment -> {
            comment.setComment(requestBody.getComment());
            comment.setBookid(requestBody.getBookid());
            return bookCommentRepository.save(comment);
        })
                .map(bookComment -> new ResponseEntity<>(bookComment, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value="/bookcomments")
    public Mono<BookComment> saveBookComment(@RequestBody BookComment requestBody){
        return bookCommentRepository.save(requestBody);
    }

}