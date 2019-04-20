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
import ru.otus.domain.Author;
import ru.otus.instance_service.AuthorCUService;
import ru.otus.ioservice.IOService;
import ru.otus.repository.AuthorRepository;
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
    private static final  String AUTHORSTRING = "Author(id=1, firstName=Dan, lastName=Simmons, yearsOfLife=1948-)";
    private static final  String AUTHORSTRING1 = "Author(id=99, firstName=Fedor, lastName=Dostoevsky, yearsOfLife=1821-1881)";
    private static final  String AUTHORSTRING2 = "Author(id=88, firstName=Viktor, lastName=Pelevin, yearsOfLife=1962 )";
    private static final  String AUTHORSTRING3 =  "Author(id=77, firstName=Alexander, lastName=Filipenko, yearsOfLife=1984-)";
    private static final  String BOOKLISTSTRING = "Book(id=7, name=Red Cross, author=Alexander Filipenko, genre=Historical Drama, literaryForm=Novel)";

    @MockBean
    ShellInputMatcher shellInputMatcher;
    @MockBean
    AuthorCUService authorCUService;
    @MockBean
    IOService ioService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AppCommands appCommands;

    @BeforeEach
    public void setUp(){
        given(shellInputMatcher.getRepository("book")).willReturn(bookRepository);
        given(shellInputMatcher.getRepository("author")).willReturn(authorRepository);
        given(shellInputMatcher.getServise(any())).willReturn(authorCUService);
        given(authorCUService.create()).willReturn(new Author("1","Dan","Simmons","1948-"));
    }

    @Test
    public void create() {
        assertEquals(AUTHORSTRING,appCommands.create("author"));
        authorRepository.delete(authorRepository.findById("1").get());
    }

    @Test
    public void showAll() {
        String commandResult = appCommands.showAll("author","");

        assertTrue(commandResult.contains(AUTHORSTRING1)
                && commandResult.contains(AUTHORSTRING2)
                && commandResult.contains(AUTHORSTRING3));
    }

    @Test
    public void getBooksByAuthor() {
        given(ioService.userInput(any())).willReturn("Alexander Filipenko");
        String commandResult = appCommands.getBooksByAuthor();
        assertEquals(BOOKLISTSTRING,commandResult);
    }

    @Test
    public void getBooksByGenre() {
        given(ioService.userInput(any())).willReturn("Historical Drama");
        String commandResult = appCommands.getBooksByGenre();
        assertEquals(BOOKLISTSTRING,commandResult);
    }

    @Test
    public void getAuthorsByGenreName() {
        given(ioService.userInput(any())).willReturn("Historical Drama");
        String commandResult = appCommands.getAuthorsByGenreName();
        assertEquals("{ \"author\" : \"Alexander Filipenko\" }",commandResult);
    }
    @Test
    public void count() {
        assertEquals("Total count of 'author' : 3",appCommands.count("author"));
    }
}