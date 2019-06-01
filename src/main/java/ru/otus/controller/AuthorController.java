package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.controller.dto.DtoConversion;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, DtoConversion<Author, AuthorDto> dtoConversion) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors() {
        List<Author> authors = authorRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        if (!authors.isEmpty()){
            return new ResponseEntity<>(authors,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable("id") long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.<ResponseEntity<?>>map(author1 -> new ResponseEntity<>(author1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/authors/{id}")
    public ResponseEntity<?> removeAuthor(@PathVariable("id") long id){
            authorRepository.deleteById(id);
       return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/authors"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editAuthor(@RequestBody AuthorDto requestBody){
        if (authorRepository.findById(requestBody.getId()).isPresent()){
            authorRepository.save(new Author(requestBody.getId(), requestBody.getFirstName(), requestBody.getLastName()));
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value="/authors"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveAuthor(@RequestBody AuthorDto requestBody){
        authorRepository.save(new Author( requestBody.getFirstName(), requestBody.getLastName()));
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }
}
