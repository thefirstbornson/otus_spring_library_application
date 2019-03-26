package ru.otus.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = {
//        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
//        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
//})
@JdbcTest
@ComponentScan({"ru.otus.dao"})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource( "classpath:test-application.properties")
public class AuthorDaoJDBCImplTest {

    private static final String NAME = "Fedor";
    private static final String SURNAME = "Dostoevsky";
    private static final String NAME2 = "Viktor";
    private static final String SURNAME2 = "Pelevin";
    private static final String NAME3 = "Alexander";
    private static final String SURNAME3 = "Filipenko";
    private static final String NAME4 = "Boris";
    private static final String SURNAME4 = "Akunin";

    @Autowired
    AuthorDao authorDataJDBC;
    Author author;

    @Before
    public void setUp(){
        author = new Author(NAME,SURNAME);

    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) authorDataJDBC.getCount());
    }

    @Test
    public void findAllTest(){
        Author[] authArr = {author,new Author(NAME2,SURNAME2),new Author(NAME3,SURNAME3)};
        List<String> testAuthorsNames = Arrays.asList(authArr)
                                         .stream()
                                         .map(e->e.getFirstName())
                                         .collect(Collectors.toList());
        List<String> dbAuthorsNames = authorDataJDBC.findAll().stream()
                                         .map(e->e.getFirstName())
                                         .collect(Collectors.toList());
        Assert.assertTrue(testAuthorsNames.containsAll(dbAuthorsNames)
                          && dbAuthorsNames.containsAll(testAuthorsNames));
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
        Author result = authorDataJDBC.findById(99);
        Assert.assertTrue(result.getFirstName().equals(NAME));
        Assert.assertTrue(result.getLastName().equals(SURNAME));
    }


    @Test
    public void updateTest(){
        author=authorDataJDBC.findById(99);
        author.setFirstName(NAME4);
        author.setLastName(SURNAME4);
        authorDataJDBC.update(author);
        Assert.assertTrue( authorDataJDBC.findById(author.getId())
                .getFirstName().equals(NAME4));
        Assert.assertTrue( authorDataJDBC.findById(author.getId())
                .getLastName().equals(SURNAME4));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteTest(){
        authorDataJDBC.save(author);
        authorDataJDBC.delete(author);
        authorDataJDBC.findById(author.getId());
    }
}