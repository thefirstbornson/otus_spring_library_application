package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BookController.class, secure = false)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    Book book;
    List<Book> books;

    @BeforeEach
    public void setUp(){
        book = new Book("Snuff",new Author("Viktor","Pelevin"), new Genre("sci-fi"));
        books = new ArrayList<>(
                List.of(new Book("Red Cross",new Author("Alexander","Filipenko"), new Genre("drama"))
                        ,new Book("Aristonomia",new Author("Boris","Akunin"), new Genre("drama"))));

    }

    @Test
    void getAllBooks() throws Exception {
        given(bookRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(books);
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Aristonomia")))
                .andExpect(view().name(containsString("books")))
                .andExpect(model().attribute("books", books))
        ;
        verify(bookRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));

        ;
    }

    @Test
    void removeBook() throws Exception {
        mvc.perform(post("/removebook")
                .param("id", "1"))
                .andExpect(redirectedUrl("books"));
    }

    @Test
    void editBook() throws Exception {
        given(authorRepository.findAll()).willReturn(List.of(new Author(1,"Leo","Tolstoy")));
        given(genreRepository.findAll()).willReturn(List.of(new Genre(1,"drama")));
        given(bookRepository.findById(any()))
                .willReturn( Optional.of(book));
        mvc.perform(post("/editbook").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Snuff")));
    }

    @Test
    void saveBook() throws Exception {
        given(bookRepository.findById(any()))
                .willReturn( Optional.of(book));
        given(authorRepository.findById(any()))
                .willReturn(Optional.of(new Author("Viktor","Pelevin")));
        given(genreRepository.findById(any()))
                .willReturn(Optional.of(new Genre("sci-fi")));

        mvc.perform(post("/savebook")
                .param("id", "1")
                .param("name", "Snuff")
                .param("authorID", "1")
                .param("genreID", "1"))
                .andExpect(redirectedUrl("books"));
    }
}