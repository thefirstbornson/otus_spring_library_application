package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.NoEntityException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Controller
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookController(BookRepository bookRepository1, AuthorRepository authorRepository, GenreRepository genreRepository) {

        this.bookRepository = bookRepository1;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/books")
    public String getAllBooks( Model model) {
        List<Book> books = bookRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/removebook")
    public String removeBook( @RequestParam("id") long id) {
        bookRepository.deleteById(id);
        return "redirect:books";
    }

    @PostMapping("/editbook")
    public String editBook(@RequestParam("id") long id, Model model){
        Book book=null;
        if (id>0) {
            try {
                book = bookRepository.findById(id).orElseThrow(NoEntityException::new);
                model.addAttribute("book", book);
            } catch (NoEntityException e) {
                e.printStackTrace();
            }
        }
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "neweditbook";
    }

    @PostMapping("/savebook")
    public String saveBook(@RequestParam("id") long id
                          ,@RequestParam("name") String name
                          ,@RequestParam("authorID") long authorID
                          ,@RequestParam("genreID") long genreID
    ){
        Book book=null;
        try {

            if (bookRepository.existsById(id)){
                book = bookRepository.findById(id).get();
                book.setName(name);
                book.setAuthor(authorRepository.findById(authorID).orElseThrow(NoEntityException::new));
                book.setGenre(genreRepository.findById(genreID).orElseThrow(NoEntityException::new));
            } else {
                book = new Book(
                        name
                        , authorRepository.findById(authorID).orElseThrow(NoEntityException::new)
                        , genreRepository.findById(genreID).orElseThrow(NoEntityException::new)
                );

            }
        } catch (NoEntityException e) {
            e.printStackTrace();
        }
        bookRepository.save(book);
        return "redirect:books";
    }

}
