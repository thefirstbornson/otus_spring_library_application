package ru.otus.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan({"ru.otus.dao"})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource( "classpath:test-application.properties")
public class GenreDaoJDBCImplTest {
    private static final String NAME = "Drama";
    private static final String NAME2 = "Sci-fi";
    private static final String NAME3 = "Historical Drama";


    @Autowired
    GenreDao genreDaoJDBC;
    Genre genre;

    @Before
    public void setUp(){
        genre = new Genre(NAME);

    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) genreDaoJDBC.getCount());
    }

    @Test
    public void findAllTest(){
        Genre[] authArr = {genre,new Genre(NAME2),new Genre(NAME3)};
        List<String> testAuthorsNames = Arrays.asList(authArr)
                .stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        List<String> dbAuthorsNames = genreDaoJDBC.findAll().stream()
                .map(e->e.getGenreName())
                .collect(Collectors.toList());
        Assert.assertTrue(testAuthorsNames.containsAll(dbAuthorsNames)
                && dbAuthorsNames.containsAll(testAuthorsNames));
    }

    @Test
    public void saveTest(){
        genreDaoJDBC.save(genre);
        Assert.assertTrue(genre.getId()>0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void notFoundByIdTest(){
        Genre result = genreDaoJDBC.findById(genre.getId());
    }

    @Test
    public void findByIdTest(){
        Genre result = genreDaoJDBC.findById(99);
        Assert.assertTrue(result.getGenreName().equals(NAME));
    }


    @Test
    public void updateTest(){
        genre=genreDaoJDBC.findById(99);
        genre.setGenreName(NAME2);
        genreDaoJDBC.update(genre);
        Assert.assertTrue( genreDaoJDBC.findById(genre.getId())
                .getGenreName().equals(NAME2));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteTest(){
        genreDaoJDBC.save(genre);
        genreDaoJDBC.delete(genre);
        genreDaoJDBC.findById(genre.getId());
    }

}