package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private JacksonTester<Book> json;
    @Autowired
    private MockMvc mvc;

    @MockBean
    BookRepository bookRepository;

    private static final long ID =9;
    private static final long FAKE_ID=199;

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
    @DisplayName("Показать все книги")
    void getAllBooks() throws Exception {
        given(bookRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(books);
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Red Cross")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(bookRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("Показать книгу с ID "+9)
    void getAuthor() throws Exception {
        book.setId(ID);
        given(bookRepository.findById(ID))
                .willReturn(Optional.of(book));
        mvc.perform(get("/books/"+ ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Snuff")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(bookRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Удалить книгу с ID "+ ID)
    void removeAuthor() throws Exception {
        mvc.perform(delete("/books/"+ ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновить книгу с ID "+ ID)
    void editAuthor() throws Exception {
        book.setId(ID);
        book.setName("Another name");
        given(bookRepository.findById(ID)).willReturn(Optional.of(book));
        given(bookRepository.save(book)).willReturn(book);
        mvc.perform(put("/books/"+ ID).contentType(APPLICATION_JSON)
                .content(this.json.write(book).getJson()))
                .andExpect(content().string("{\"status\":\"updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат статуса 204 (no content) при попытке обновить автора с неактуальным ID "+FAKE_ID)
    void editAuthorfail() throws Exception {
        book.setId(FAKE_ID);
        book.setName("Another name");
        given(bookRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        mvc.perform(put("/books/"+ FAKE_ID).contentType(APPLICATION_JSON)
                .content(this.json.write(book).getJson()))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Сохранить книгу")
    void saveAuthor() throws Exception {
        book.setId(ID);
        given(bookRepository.save(book)).willReturn(book);
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(this.json.write(book).getJson()))
                .andExpect(content().string("{\"status\":\"saved\"}"))
                .andExpect(status().isCreated());
    }
}