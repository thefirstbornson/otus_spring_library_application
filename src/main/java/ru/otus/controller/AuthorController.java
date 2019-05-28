package ru.otus.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.controller.dto.AuthorDto;
import ru.otus.controller.dto.DtoConversion;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/authors")
    @ResponseBody
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        return authors.stream().map(e->new DtoConversion<Author, AuthorDto>(modelMapper).convertToDto(e,AuthorDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping(value="/authors/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String removeAuthor(@PathVariable("id") long id){
        try {
            authorRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return "{\"state\":\"failed\"}";
        }
        return "{\"state\":\"deleted\"}";
    }

    @PutMapping(value="/authors/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public AuthorDto editAuthor(@PathVariable("id") long id){
        Author author = authorRepository.findById(id).orElse(null);
        return new DtoConversion<Author, AuthorDto>(modelMapper).convertToDto(author, AuthorDto.class);
    }

    @PostMapping(value="/authors", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String saveAuthor(@RequestBody JsonNode requestBody){
        long id = requestBody.get("id").longValue();
        String firstName = requestBody.get("fname").toString();
        String lastName = requestBody.get("lname").toString();

        Author author = authorRepository.findById(id).map(a -> new Author(a.getId(), firstName, lastName)).orElse(new Author(firstName, lastName));
        authorRepository.save(author);
        return "{\"state\":\"saved\"}";
    }
}
