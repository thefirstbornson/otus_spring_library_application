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
import ru.otus.domain.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"ru.otus.dao"})
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class BookDaoJDBCImplTest {
    private static final String NAME = "Anna Karenina";
    private static final String NAME2 = "Snuff";
    private static final String NAME3 = "Red Cross";
    private static final String NAME4 = "Aristonomia";

    @Autowired
    BookDao bookDaoJpa;
    @Autowired
    AuthorDao authorDaoJpa;
    @Autowired
    GenreDao genreDaoJpa;
    Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book(NAME
                , authorDaoJpa.findById(99)
                , genreDaoJpa.findById(99)
        );
    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) bookDaoJpa.getCount());
    }

    @Test
    public void findAllTest(){
        Book[] bookArr = {book,new Book(NAME2, authorDaoJpa.findById(88), genreDaoJpa.findById(88))
                ,new Book(NAME3, authorDaoJpa.findById(77), genreDaoJpa.findById(77))};
        List<String> testBooksNames = Arrays.asList(bookArr)
                .stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        List<String> dbBookNames = bookDaoJpa.findAll().stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        Assert.assertTrue(testBooksNames.containsAll(dbBookNames)
                && dbBookNames.containsAll(testBooksNames));
    }

    @Test
    public void saveTest(){
        bookDaoJpa.save(book);
        Assert.assertTrue(book.getId()>0);
    }

    @Test
    public void notFoundByIdTest(){
        Assert.assertNull(bookDaoJpa.findById(book.getId()));
    }

    @Test
    public void findByIdTest(){
        Book result = bookDaoJpa.findById(99);
        Assert.assertTrue(result.getName().equals(NAME));
    }


    @Test
    public void updateTest(){

        book= bookDaoJpa.findById(99);
        book.setName(NAME4);
        bookDaoJpa.update(book);
        Assert.assertTrue( bookDaoJpa.findById(book.getId())
                .getName().equals(NAME4));
    }

    @Test
    public void deleteTest(){
        bookDaoJpa.save(book);
        Long id = book.getId();
        bookDaoJpa.delete(book);
        Assert.assertNull(bookDaoJpa.findById(id));
    }

}