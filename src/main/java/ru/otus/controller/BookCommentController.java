package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;

import java.util.List;

@Controller
public class BookCommentController {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookCommentController(BookCommentRepository bookCommentRepository, BookRepository bookRepository) {

        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/bookcomments")
    public String getAllBookComments( Model model) {
        List<BookComment> bookComments = bookCommentRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        model.addAttribute("bookcomments", bookComments);
        return "bookcomments";
    }

    @PostMapping("/removebookcomment")
    public String removebookcomment( @RequestParam("id") long id) {
        try {
            bookCommentRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }

        return "redirect:bookcomments";
    }

    @PostMapping("/editbookcomment")
    public String editbookcomment(@RequestParam("id") long id, Model model){
        BookComment bookComment= bookCommentRepository.findById(id).orElse(null);
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bookcomment", bookComment);
        return "neweditbookcomment";
    }

    @PostMapping("/savebookcomment")
    public String saveBookcomment(@RequestParam("id") long id
                          ,@RequestParam("bookcomment") String bcomment
                          ,@RequestParam("bookID") long bookID
    ){
        BookComment bookComment  = bookCommentRepository.findById(id).map(bc -> new BookComment(bc.getId(), bcomment, bookRepository.findById(bookID).get()))
                                                                     .orElse(new BookComment(bcomment, bookRepository.findById(bookID).get()));
        bookCommentRepository.save(bookComment);

        return "redirect:bookcomments";
    }
}
