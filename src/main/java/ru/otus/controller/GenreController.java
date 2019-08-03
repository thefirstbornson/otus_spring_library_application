package ru.otus.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Collections;
import java.util.List;

@Controller
public class GenreController {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    @HystrixCommand(fallbackMethod = "defaultGetGenreStub")
    public String getAllGenres( Model model) {
        List<Genre> genres = genreRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("genres", genres);
        return "genres";
    }

    private String defaultGetGenreStub(Model model) {
        List<Genre> genres = Collections.singletonList(
                new Genre("no db connection"));
        model.addAttribute("genres", genres);
        return "genres";
    }

    @PostMapping("/removegenre")
    @HystrixCommand(fallbackMethod = "defaultRemoveGenreStub")
    public String removeGenre( @RequestParam("id") long id) {
        try {
            genreRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }
        return "redirect:genres";
    }

    private String defaultRemoveGenreStub(long id) {
        return "redirect:genres";
    }

    @PostMapping("/editgenre")
    @HystrixCommand(fallbackMethod = "defaultEditGenreStub")
    public String editGenre(@RequestParam("id") long id, Model model){
        Genre genre = genreRepository.findById(id).orElse(null);
        model.addAttribute("genre", genre);
        return "neweditgenre";
    }

    private String defaultEditGenreStub(long id, Model model) {
        return "redirect:genres";
    }

    @PostMapping("/savegenre")
    @HystrixCommand(fallbackMethod = "defaultSaveGenreStub")
    public String saveGenre(@RequestParam("id") long id
            ,@RequestParam("genrename") String genrename){
        Genre genre = genreRepository.findById(id).map(g -> new Genre(g.getId(), genrename)).orElse(new  Genre(genrename));
        genreRepository.save(genre);
        return "redirect:genres";
    }

    private String defaultSaveGenreStub(long id,  String genrename) {
        return "redirect:books";
    }
}
