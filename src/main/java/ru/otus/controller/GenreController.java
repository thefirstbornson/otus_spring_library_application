package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.dto.DtoConversion;
import ru.otus.controller.dto.GenreDto;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class GenreController {
    private final GenreRepository genreRepository;
    @Autowired
    public GenreController(GenreRepository genreRepository, DtoConversion<Genre,GenreDto> dtoConversion) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        List<Genre> genres = genreRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        if (!genres.isEmpty()){
            return new ResponseEntity<>(genres,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<?> getGenre(@PathVariable("id") long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.<ResponseEntity<?>>map(genre1 -> new ResponseEntity<>(genre1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("{\"status\":\"not found\"}", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/genres/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable("id") long id){
        genreRepository.deleteById(id);
        return new ResponseEntity<>("{\"status\":\"deleted\"}", HttpStatus.OK);
    }

    @PutMapping(value="/genres"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> editGenre(@RequestBody GenreDto requestBody){
        if (genreRepository.findById(requestBody.getId()).isPresent()){
            genreRepository.save(new Genre(requestBody.getId(),  requestBody.getGenreName()));
            return (new ResponseEntity<>("{\"status\":\"updated\"}", HttpStatus.OK));
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value="/genres"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveGenre(@RequestBody GenreDto requestBody){
        genreRepository.save(new Genre( requestBody.getGenreName()));
        return new ResponseEntity<>("{\"status\":\"saved\"}", HttpStatus.CREATED);
    }
}
