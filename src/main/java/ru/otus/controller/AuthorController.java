package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public String getAllAuthors( Model model) {
        List<Author> authors = authorRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("authors", authors);
        return "authors";
    }

    @PostMapping("/removeauthor")
    public String removeAuthor( @RequestParam("id") long id) {
        try {
            authorRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }

        return "redirect:authors";
    }

    @PostMapping("/editauthor")
    public String editAuthor(@RequestParam("id") long id, Model model){
        Author author = authorRepository.findById(id).orElse(null);
        model.addAttribute("author", author);
        return "neweditauthor";
    }

    @PostMapping("/saveauthor")
    public String saveAuthor(@RequestParam("id") long id
                          ,@RequestParam("fname") String firstName
                          ,@RequestParam("lname") String lastName
    ){
        Author author = authorRepository.findById(id).map(a -> new Author(a.getId(), firstName, lastName)).orElse(new Author(firstName, lastName));
        authorRepository.save(author);
        return "redirect:authors";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
