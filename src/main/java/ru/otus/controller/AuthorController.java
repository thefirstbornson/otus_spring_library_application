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
import ru.otus.exception.NoEntityException;
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

    @GetMapping("/removeauthor")
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
        Author author=null;
        if (id>0) {
            try {
                author = authorRepository.findById(id).orElseThrow(NoEntityException::new);
            } catch (NoEntityException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("author", author);
        return "neweditauthor";
    }

    @PostMapping("/saveauthor")
    public String saveAuthor(@RequestParam("id") long id
                          ,@RequestParam("fname") String firstName
                          ,@RequestParam("lname") String lastName
    ){
            Author author;
            if (authorRepository.existsById(id)){
                author = authorRepository.findById(id).get();
                author.setFirstName(firstName);
                author.setLastName(lastName);
            } else {
                author = new Author(firstName, lastName);
            }
        authorRepository.save(author);
        return "redirect:authors";
    }
}
