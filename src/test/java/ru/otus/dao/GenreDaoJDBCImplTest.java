package ru.otus.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"ru.otus.dao"})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class GenreDaoJDBCImplTest {
    private static final String NAME = "Drama";
    private static final String NAME2 = "Sci-fi";
    private static final String NAME3 = "Historical Drama";


    @Autowired
    GenreDao genreDaoJpa;
    Genre genre;

    @Before
    public void setUp(){
        genre = new Genre(NAME);

    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) genreDaoJpa.getCount());
    }

    @Test
    public void findAllTest(){
        Genre[] authArr = {genre,new Genre(NAME2),new Genre(NAME3)};
        List<String> testAuthorsNames = Arrays.asList(authArr)
                .stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        List<String> dbAuthorsNames = genreDaoJpa.findAll().stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        Assert.assertTrue(testAuthorsNames.containsAll(dbAuthorsNames)
                && dbAuthorsNames.containsAll(testAuthorsNames));
    }

    @Test
    public void saveTest(){
        genreDaoJpa.save(genre);
        Assert.assertTrue(genre.getId()>0);
    }

    @Test
    public void notFoundByIdTest(){
        Assert.assertNull(genreDaoJpa.findById(genre.getId()));
    }

    @Test
    public void findByIdTest(){
        Genre result = genreDaoJpa.findById(99);
        Assert.assertTrue(result.getGenreName().equals(NAME));
    }


    @Test
    public void updateTest(){
        genre= genreDaoJpa.findById(99);
        genre.setGenreName(NAME2);
        genreDaoJpa.update(genre);
        Assert.assertTrue( genreDaoJpa.findById(genre.getId())
                .getGenreName().equals(NAME2));

    }

    @Test
    public void deleteTest(){
        genreDaoJpa.save(genre);
        Long id = genre.getId();
        genreDaoJpa.delete(genre);
        Assert.assertNull(genreDaoJpa.findById(id));
    }

}