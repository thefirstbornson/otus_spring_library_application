package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.config.SadFilter;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Controller
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final QueueChannel bookDramaChannel;
    private final SadFilter sadFilter;

    @Autowired
    public BookController(BookRepository bookRepository1, AuthorRepository authorRepository
            , GenreRepository genreRepository, QueueChannel bookDramaChannel, SadFilter sadFilter) {

        this.bookRepository = bookRepository1;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookDramaChannel = bookDramaChannel;
        this.sadFilter = sadFilter;
    }

    @GetMapping("/books")
    public String getAllBooks( Model model) {
        List<Book> books = bookRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        //bookDramaChannel.send(books);
        sadFilter.bookDrama(books);
        Message<?> outMessage = bookDramaChannel.receive(0);
        model.addAttribute("books", outMessage.getPayload());
        return "books";
    }

    @PostMapping("/removebook")
    public String removeBook( @RequestParam("id") long id) {
        bookRepository.deleteById(id);
        return "redirect:books";
    }

    @PostMapping("/editbook")
    public String editBook(@RequestParam("id") long id, Model model){

        Book book = bookRepository.findById(id).orElse(null);
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "neweditbook";
    }

    @PostMapping("/savebook")
    public String saveBook(@RequestParam("id") long id
                          ,@RequestParam("name") String name
                          ,@RequestParam("authorID") long authorID
                          ,@RequestParam("genreID") long genreID
    ){
        Book book = authorRepository.findById(id).map(b -> new Book(b.getId()
                , name
                , authorRepository.findById(authorID).get()
                , genreRepository.findById(genreID).get()))
                .orElse(new Book(name
                        , authorRepository.findById(authorID).get()
                        , genreRepository.findById(genreID).get()));
        bookRepository.save(book);
        return "redirect:books";
    }

}
