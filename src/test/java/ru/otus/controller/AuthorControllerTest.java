package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private static final String NAME1 = "Fedor";
    private static final String SURNAME1 = "Dostoevsky";
    private static final String NAME2 = "Viktor";
    private static final String SURNAME2 = "Pelevin";
    private static final String NAME3 = "Alexander";
    private static final String SURNAME3 = "Filipenko";
    private static final String NAME4 = "Boris";
    private static final String SURNAME4 = "Akunin";

    @Autowired
    private MockMvc mvc;

    @MockBean
    AuthorRepository authorRepository;
    Author author;
    List<Author> authors;

    @BeforeEach
    public void setUp(){
        author = new Author(NAME1, SURNAME1);
        authors = new ArrayList<>(
                List.of(new Author(1,NAME1,SURNAME1)
                       ,new Author(2, NAME2, SURNAME2)
                        ,new Author(3,NAME3,SURNAME3)));

    }

    @Test
    void getAllAuthors() throws Exception {
        given(authorRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(authors);
        mvc.perform(get("/authors"))
                            .andExpect(status().isOk())
                            .andExpect(content().string(containsString("Fedor")))
        ;
    }

    @Test
    void removeAuthor() throws Exception {
        mvc.perform(get("/removeauthor")
                .param("id", "1"))
                .andExpect(redirectedUrl("authors"));
    }

    @Test
    void editAuthor() throws Exception {
        given(authorRepository.findById(any()))
                .willReturn( Optional.of(author));
        mvc.perform(post("/editauthor").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fedor")));
    }

    @Test
    void saveAuthor() throws Exception {
        mvc.perform(post("/saveauthor")
                .param("id", "1")
                .param("fname", "Fedor")
                .param("lname", "Dostoevsky"))
                .andExpect(redirectedUrl("authors"));
    }
}