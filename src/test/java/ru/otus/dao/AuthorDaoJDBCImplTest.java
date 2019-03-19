package ru.otus.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {
//        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
//        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
//})
public class AuthorDaoJDBCImplTest {

    @Autowired
    AuthorDao authorDataJDBC;
    Author author;

    @Before
    public void setUp(){
        author = new Author("Fedor","Dostoevsky");
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
        System.out.println(authorDataJDBC.save(author));
    }

    @Test
    public void updateTest(){
        authorDataJDBC.save(author);
        author.setAuthor_first_name("Artem");
        author.setAuthor_last_name("Poskrebyshev");
        authorDataJDBC.update(author);
        System.out.println(author);
    }

    @Test
    public void deleteTest(){
        authorDataJDBC.save(author);
        System.out.println("Try to delete "+author);
        authorDataJDBC.delete(author);
    }


}