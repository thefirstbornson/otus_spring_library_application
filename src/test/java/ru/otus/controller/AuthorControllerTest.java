package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
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
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private JacksonTester<Author> json;

    private static final String NAME1 = "Fedor";
    private static final String SURNAME1 = "Dostoevsky";
    private static final String NAME2 = "Viktor";
    private static final String SURNAME2 = "Pelevin";
    private static final String NAME3 = "Alexander";
    private static final String SURNAME3 = "Filipenko";
    private static final String NAME4 = "Boris";
    private static final String SURNAME4 = "Akunin";
    private static final long ID =99;
    private static final long FAKE_ID=199;

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
    @DisplayName("Показать всех авторов")
    void getAllAuthors() throws Exception {
        given(authorRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(authors);
        mvc.perform(get("/authors"))
                            .andExpect(status().isOk())
                            .andExpect(content().string(containsString(NAME1)))
                            .andExpect(content()
                            .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(authorRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("Показать автора с ID "+ID)
    void getAuthor() throws Exception {
        author.setId(ID);
        given(authorRepository.findById(ID))
                .willReturn(Optional.of(author));
        mvc.perform(get("/authors/"+ ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME1)))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(authorRepository, Mockito.times(1)).findById(99L);
    }

    @Test
    @DisplayName("Удалить автора с ID "+ ID)
    void removeAuthor() throws Exception {
        mvc.perform(delete("/authors/"+ ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновить автора с ID "+ ID)
    void editAuthor() throws Exception {
        author.setId(ID);
        author.setFirstName(NAME2);
        author.setLastName(SURNAME2);
        given(authorRepository.findById(ID)).willReturn(Optional.of(author));
        given(authorRepository.save(author)).willReturn(author);
        mvc.perform(put("/authors/"+ ID).contentType(APPLICATION_JSON)
                    .content(this.json.write(author).getJson()))
                    .andExpect(content().string("{\"status\":\"updated\"}"))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат статуса 204 (no content) при попытке обновить автора с неактуальным ID "+FAKE_ID)
    void editAuthorfail() throws Exception {
        author.setId(FAKE_ID);
        author.setFirstName(NAME2);
        author.setLastName(SURNAME2);
        given(authorRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        mvc.perform(put("/authors/"+ FAKE_ID).contentType(APPLICATION_JSON)
                .content(this.json.write(author).getJson()))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Сохранить автора")
    void saveAuthor() throws Exception {
        author.setId(ID);
        given(authorRepository.save(author)).willReturn(author);
        mvc.perform(post("/authors").contentType(APPLICATION_JSON)
                .content(this.json.write(author).getJson()))
                .andExpect(content().string("{\"status\":\"saved\"}"))
                .andExpect(status().isCreated());
    }
}