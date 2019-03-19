package ru.otus.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@JdbcTest(properties = {
//        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
//        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
//})



//@SpringBootTest(properties = {
//        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
//        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
//})

@JdbcTest
@Import({BookDaoJDBCImpl.class,AuthorDaoJDBCImpl.class,GenreDaoJDBCImpl.class})
public class BookDaoJDBCImplTest {
    @Autowired
    BookDao bookDaoJDBC;
    @Autowired
    AuthorDao authorDaoJDBC;
    @Autowired
    GenreDao genreDaoJDBC;

    Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book("Snuff"
                , authorDaoJDBC.save(new Author("Pelevin", "Viktor"))
                , genreDaoJDBC.save(new Genre("Sci-fi"))
        );
    }

    @Test
    public void save() {
        System.out.println(bookDaoJDBC.save(book));
    }

    @Test
    public void update() {
        bookDaoJDBC.save(book);
        book.setGenre(genreDaoJDBC.findById(1));
        bookDaoJDBC.update(book);
    }

    @Test
    public void delete() {
        bookDaoJDBC.save(book);
        System.out.println("Try to delete "+book);
        bookDaoJDBC.delete(book);
    }

    @Test
    public void findById(){
        System.out.println(bookDaoJDBC.findById(99));
    }

    @Test
    public void findAll(){
        System.out.println(bookDaoJDBC.findAll());
    }

}