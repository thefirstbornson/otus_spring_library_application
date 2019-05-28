package ru.otus.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.dto.DtoConversion;
import ru.otus.controller.dto.GenreDto;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreController {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreController(GenreRepository genreRepository, ModelMapper modelMapper) {

        this.genreRepository = genreRepository;

        this.modelMapper = modelMapper;
    }

    @GetMapping("/genres")
    @ResponseBody
    public List<GenreDto> getAllGenres() {
        List<Genre> genres = genreRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        return genres.stream().map(e->new DtoConversion<Genre, GenreDto>(modelMapper).convertToDto(e,GenreDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(value="/genres/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String removeGenre( @PathVariable("id") long id) {
        try {
            genreRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return "{\"state\":\"failed\"}";
        }
        return "{\"state\":\"deleted\"}";
    }

    @PutMapping(value="/genres/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public GenreDto editGenre(@PathVariable("id") long id){
        Genre genre = genreRepository.findById(id).orElse(null);
        return new DtoConversion<Genre, GenreDto>(modelMapper).convertToDto(genre,GenreDto.class);
    }

    @PostMapping(value="/genres", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String saveGenre(@RequestBody JsonNode requestBody){
        long id = requestBody.get("id").longValue();
        String genreName = requestBody.get("genreName").toString();

        Genre genre = genreRepository.findById(id).map(g -> new Genre(g.getId(), genreName)).orElse(new  Genre(genreName));
        genreRepository.save(genre);
        return  "{\"state\":\"saved\"}";
    }
}
