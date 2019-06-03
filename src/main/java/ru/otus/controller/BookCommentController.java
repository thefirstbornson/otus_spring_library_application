package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.BookComment;
import ru.otus.repository.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class BookCommentController {
    private final BookCommentRepository bookCommentRepository;

    @Autowired
    public BookCommentController(BookCommentRepository bookCommentRepository) {
        this.bookCommentRepository = bookCommentRepository;
    }

    @GetMapping("/bookcomments")
    public ResponseEntity<?> getAllBookComments() {
        List<BookComment> bookComments = bookCommentRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        if (!bookComments.isEmpty()){
            return new ResponseEntity<>(bookComments,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bookcomments/{id}")
    public ResponseEntity<?> getBookComment(@PathVariable("id") long id) {
        Optional<BookComment> bookComment = bookCommentRepository.findById(id);
        return bookComment.<ResponseEntity<?>>map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/bookcomments/{id}")
    public ResponseEntity<?> removeBookComment(@PathVariable("id") long id){
        bookCommentRepository.deleteById(id);
        return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/bookcomments/{id}"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editBookComment(@PathVariable("id") long id,@RequestBody BookComment requestBody){
        if (bookCommentRepository.findById(id).isPresent()){
            bookCommentRepository.save(new BookComment(id
                                                        ,requestBody.getComment()
                                                        ,requestBody.getBook()));
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value="/bookcomments"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveBookComment(@RequestBody BookComment requestBody){
        bookCommentRepository.save(new BookComment(requestBody.getId()
                , requestBody.getComment()
                ,requestBody.getBook()));
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }
}