package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan({"ru.otus.dao"})
@TestPropertySource("classpath:application-test.properties")
public class GenreDaoJpaImplTest {
    private static final String NAME = "Drama";
    private static final String NAME2 = "Sci-fi";
    private static final String NAME3 = "Historical Drama";


    @Autowired
    GenreRepository genreRepository;
    Genre genre;

    @BeforeEach
    public void setUp(){
        genre = new Genre(NAME);
    }

    @Test
    public void getCountTest(){
        assertEquals(3L,(long) genreRepository.count());
    }

    @Test
    public void findAllTest(){
        Genre[] authArr = {genre,new Genre(NAME2),new Genre(NAME3)};
        List<String> testAuthorsNames = Arrays.asList(authArr)
                .stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        List<String> dbAuthorsNames = genreRepository.findAll().stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        assertThat(testAuthorsNames).containsExactlyInAnyOrderElementsOf(dbAuthorsNames);
    }

    @Test
    public void saveTest(){
        genreRepository.save(genre);
        assertTrue(genre.getId()>0);
    }

    @Test
    public void shouldRaiseAnNoSuchElementException() throws NoSuchElementException {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            genreRepository.findById(genre.getId()).get();
        });
    }

    @Test
    public void findByIdTest(){
        Genre result = genreRepository.findById(999L).get();
        assertEquals(NAME,result.getGenreName());
    }


    @Test
    public void updateTest(){
        genre= genreRepository.findById(999L).get();
        genre.setGenreName(NAME2);
        genreRepository.save(genre);
        assertEquals(NAME2, genreRepository.findById(genre.getId()).get().getGenreName());
    }

    @Test
    public void deleteTest(){
        genreRepository.save(genre);
        Long id = genre.getId();
        genreRepository.delete(genre);
        assertTrue(!genreRepository.findById(id).isPresent());
    }

}