package ru.otus.shell;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Book;
import ru.otus.instance_service.BookCUService;
import ru.otus.ioservice.IOService;
import ru.otus.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.testconfig", "ru.otus.repository","ru.otus.shell"})

public class AppCommandsTest {
    private static final  String BOOKSTRING = "Book(id=1, name=Terror, author=Dan Simmons, genre=Historical Drama, literaryForm=Novel)";
    private static final  String BOOKSTRING1 = "Book(id=9, name=Anna Karenina, author=Leo Tolstoy, genre=Drama, literaryForm=Novel)";
    private static final  String BOOKSTRING2 = "Book(id=8, name=Snuff, author=Viktor Pelevin, genre=Sci-fi, literaryForm=Novel)";
    private static final  String BOOKSTRING3 = "Book(id=7, name=Red Cross, author=Alexander Filipenko, genre=Historical Drama, literaryForm=Novel)";

    @MockBean
    ShellInputMatcher shellInputMatcher;

    @MockBean
    BookCUService bookCUService;
    @MockBean
    IOService ioService;


    @Autowired
    BookRepository bookRepository;

    @Autowired
    AppCommands appCommands;

    @BeforeEach
    public void setUp(){
        given(shellInputMatcher.getRepository("book")).willReturn(bookRepository);
        given(shellInputMatcher.getServise(any())).willReturn(bookCUService);
        given(bookCUService.create()).willReturn(new Book("1","Terror","Dan Simmons","Historical Drama", "Novel"));
    }

    @Test
    public void create() {
        assertEquals(BOOKSTRING,appCommands.create("book"));
        bookRepository.delete(bookRepository.findById("1").get());
    }

    @Test
    public void showAll() {
        String commandResult = appCommands.showAll("book","");

        assertTrue(commandResult.contains(BOOKSTRING1)
                && commandResult.contains(BOOKSTRING2)
                && commandResult.contains(BOOKSTRING3));
    }

    @Test
    public void getBooksByAuthor() {
        given(ioService.userInput(any())).willReturn("Alexander Filipenko");
        String commandResult = appCommands.getBooksByAuthor();
        assertEquals(BOOKSTRING3,commandResult);
    }

    @Test
    public void getBooksByGenre() {
        given(ioService.userInput(any())).willReturn("Historical Drama");
        String commandResult = appCommands.getBooksByGenre();
        assertEquals(BOOKSTRING3,commandResult);
    }

    @Test
    public void getAuthorsByGenreName() {
        given(ioService.userInput(any())).willReturn("Historical Drama");
        String commandResult = appCommands.getAuthorsByGenreName();
        assertEquals("{ \"author\" : \"Alexander Filipenko\" }",commandResult);
    }
    @Test
    public void count() {
        assertEquals("Total count of 'book' : 3",appCommands.count("book"));
    }
}