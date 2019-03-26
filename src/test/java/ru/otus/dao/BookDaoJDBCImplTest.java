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
import org.springframework.context.annotation.Import;
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
public class BookDaoJDBCImplTest {
    private static final String NAME = "Anna Karenina";
    private static final String NAME2 = "Snuff";
    private static final String NAME3 = "Red Cross";
    private static final String NAME4 = "Aristonomia";

    @Autowired
    BookDao bookDaoJDBC;
    @Autowired
    AuthorDao authorDaoJDBC;
    @Autowired
    GenreDao genreDaoJDBC;
    Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book(NAME
                , authorDaoJDBC.findById(99)
                , genreDaoJDBC.findById(99)
        );
    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) bookDaoJDBC.getCount());
    }

    @Test
    public void findAllTest(){
        Book[] bookArr = {book,new Book(NAME2,authorDaoJDBC.findById(88),genreDaoJDBC.findById(88))
                ,new Book(NAME3,authorDaoJDBC.findById(77),genreDaoJDBC.findById(77))};
        List<String> testBooksNames = Arrays.asList(bookArr)
                .stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        List<String> dbBookNames = bookDaoJDBC.findAll().stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        Assert.assertTrue(testBooksNames.containsAll(dbBookNames)
                && dbBookNames.containsAll(testBooksNames));
    }

    @Test
    public void saveTest(){
        bookDaoJDBC.save(book);
        Assert.assertTrue(book.getId()>0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void notFoundByIdTest(){
        Book result = bookDaoJDBC.findById(book.getId());
    }

    @Test
    public void findByIdTest(){
        Book result = bookDaoJDBC.findById(99);
        Assert.assertTrue(result.getName().equals(NAME));
    }


    @Test
    public void updateTest(){
        book=bookDaoJDBC.findById(99);
        book.setName(NAME4);
        bookDaoJDBC.update(book);
        Assert.assertTrue( bookDaoJDBC.findById(book.getId())
                .getName().equals(NAME4));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteTest(){
        bookDaoJDBC.save(book);
        bookDaoJDBC.delete(book);
        bookDaoJDBC.findById(book.getId());
    }

}