package ru.otus.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class GenreDaoJDBCImplTest {

    @Autowired
    GenreDao genreDaoJDBC;
    Genre genre;

    @Before
    public void setUp() throws Exception {
        genre = new Genre("Fantasy");
    }

    @Test
    public void getCountTest(){
        System.out.println(genreDaoJDBC.getCount());
    }

    @Test
    public void findAllTest(){
        System.out.println(genreDaoJDBC.findAll());
    }


    @Test
    public void save() {
        System.out.println(genreDaoJDBC.save(genre));
    }

    @Test
    public void update() {
        genreDaoJDBC.save(genre);
        genre.setGenreName("Horror");
        genreDaoJDBC.update(genre);
    }

    @Test
    public void delete() {
        genreDaoJDBC.save(genre);
        System.out.println("Try to delete "+genre);
        genreDaoJDBC.delete(genre);
    }

}