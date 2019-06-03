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
import ru.otus.domain.Genre;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private JacksonTester<Genre> json;

    @Autowired
    private MockMvc mvc;

    @MockBean
    GenreRepository genreRepository;
    Genre genre;
    List<Genre> genres;

    private static final long ID =999;
    private static final long FAKE_ID=199;

    @BeforeEach
    void setUp() {
        genre = new Genre(1,"Horror");
        genres = new ArrayList<>(
                List.of(new Genre(2,"Horror")
                        ,new Genre(3, "Fantasy")
                        ,new Genre(4,"Tragedy")));


    }

    @Test
    @DisplayName("Показать все жанры")
    void getAllAuthors() throws Exception {
        given(genreRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(genres);
        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tragedy")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(genreRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("Показать жанр с ID "+ID)
    void getAuthor() throws Exception {
        genre.setId(ID);
        given(genreRepository.findById(ID))
                .willReturn(Optional.of(genre));
        mvc.perform(get("/genres/"+ ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Horror")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(genreRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Удалить жанр с ID "+ ID)
    void removeAuthor() throws Exception {
        mvc.perform(delete("/genres/"+ ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновить жанр с ID "+ ID)
    void editAuthor() throws Exception {
        genre.setId(ID);
        genre.setGenreName("another bookComment");
        given(genreRepository.findById(ID)).willReturn(Optional.of(genre));
        given(genreRepository.save(genre)).willReturn(genre);
        mvc.perform(put("/genres/"+ ID).contentType(APPLICATION_JSON)
                .content(this.json.write(genre).getJson()))
                .andExpect(content().string("{\"status\":\"updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат статуса 204 (no content) при попытке обновить жанр с неактуальным ID "+FAKE_ID)
    void editAuthorfail() throws Exception {
        genre.setId(ID);
        genre.setGenreName("another bookComment");
        given(genreRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        mvc.perform(put("/genres/"+ FAKE_ID).contentType(APPLICATION_JSON)
                .content(this.json.write(genre).getJson()))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Сохранить жанр")
    void saveAuthor() throws Exception {
        genre.setId(ID);
        given(genreRepository.save(genre)).willReturn(genre);
        mvc.perform(post("/genres").contentType(APPLICATION_JSON)
                .content(this.json.write(genre).getJson()))
                .andExpect(content().string("{\"status\":\"saved\"}"))
                .andExpect(status().isCreated());
    }
}