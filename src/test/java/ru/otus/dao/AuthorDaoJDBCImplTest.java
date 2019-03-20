package ru.otus.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {
//        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
//        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
//})
@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoJDBCImplTest {

    private static final String NAME = "Fedor";
    private static final String SURNAME = "Dostoevsky";
    private static final String NAME1 = "Viktor";
    private static final String SURNAME1 = "Pelevin";

    @Autowired
    AuthorDao authorDataJDBC;
    Author author;

    @Before
    public void setUp(){
        author = new Author(NAME,SURNAME);
    }

    @Test
    public void getCountTest(){
        System.out.println(authorDataJDBC.getCount());
    }

    @Test
    public void findAllTest(){
        System.out.println(authorDataJDBC.findAll());
    }

    @Test
    public void saveTest(){
        authorDataJDBC.save(author);
        Assert.assertTrue(author.getId()>0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void notFoundByIdTest(){
        Author result = authorDataJDBC.findById(author.getId());
    }

    @Test
    public void findByIdTest(){
        authorDataJDBC.save(author);
        Author result = authorDataJDBC.findById(author.getId());
        Assert.assertTrue(author.getAuthor_first_name().equals(NAME));
        Assert.assertTrue(author.getAuthor_last_name().equals(SURNAME));
        System.out.println(authorDataJDBC.save(author));
    }


    @Test
    public void updateTest(){
        authorDataJDBC.save(author);
        author.setAuthor_first_name(NAME1);
        author.setAuthor_last_name(SURNAME1);
        authorDataJDBC.update(author);
        Assert.assertTrue(author.getAuthor_first_name().equals(NAME1));
        Assert.assertTrue(author.getAuthor_last_name().equals(SURNAME1));
        System.out.println(author);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteTest(){
        authorDataJDBC.save(author);
        authorDataJDBC.delete(author);
        authorDataJDBC.findById(author.getId());
    }
}