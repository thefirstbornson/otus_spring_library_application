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
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
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
@WebMvcTest(value = GenreController.class, secure = false)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    GenreRepository genreRepository;
    Genre genre;
    List<Genre> genres;

    @BeforeEach
    void setUp() {
        genre = new Genre(1,"Horror");
        genres = new ArrayList<>(
                Arrays.asList(new Genre(2, "Horror")
                        , new Genre(3, "Fantasy")
                        , new Genre(4, "Tragedy")));


    }

    @Test
    void getAllGenres() throws Exception {
        given(genreRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(genres);
        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tragedy")))
                .andExpect(view().name(containsString("genres")))
                .andExpect(model().attribute("genres", genres))
        ;
        verify(genreRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
        ;
    }

    @Test
    void removeGenre() throws Exception {
        mvc.perform(post("/removegenre")
                .param("id", "1"))
                .andExpect(redirectedUrl("genres"));
    }

    @Test
    void editGenre() throws Exception {
        given(genreRepository.findById(any()))
                .willReturn( Optional.of(genre));
        mvc.perform(post("/editgenre").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Horror")));
    }

    @Test
    void saveGenre() throws Exception {
        mvc.perform(post("/savegenre")
                .param("id", "1")
                .param("genrename", "Horror"))
                .andExpect(redirectedUrl("genres"));
    }
}