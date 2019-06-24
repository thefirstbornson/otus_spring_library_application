package ru.otus.controller;

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

import java.util.List;

@Controller
public class GenreController {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {

        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    public String getAllGenres( Model model) {
        List<Genre> authors = genreRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("genres", authors);
        return "genres";
    }

    @PostMapping("/removegenre")
    public String removeGenre( @RequestParam("id") long id) {
        try {
            genreRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }
        return "redirect:genres";
    }

    @PostMapping("/editgenre")
    public String editGenre(@RequestParam("id") long id, Model model){
        Genre genre = genreRepository.findById(id).orElse(null);
        model.addAttribute("genre", genre);
        return "neweditgenre";
    }

    @PostMapping("/savegenre")
    public String saveGenre(@RequestParam("id") long id
            ,@RequestParam("genrename") String genrename

    ){
        Genre genre = genreRepository.findById(id).map(g -> new Genre(g.getId(), genrename)).orElse(new  Genre(genrename));
        genreRepository.save(genre);
        return "redirect:genres";
    }
}
