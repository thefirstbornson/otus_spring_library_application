package ru.otus.shell;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
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
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ComponentScan
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class AppCommandsTest {
    private static final  String AUTHORSTRING = "Author{id=100, firstName='Dan', lastName='Simmons'}";
    private static final  String AUTHORSTRING1 = "Author{id=99, firstName='Fedor', lastName='Dostoevsky'}";
    private static final  String AUTHORSTRING2 = "Author{id=88, firstName='Viktor', lastName='Pelevin'}";
    private static final  String AUTHORSTRING3 =  "Author{id=77, firstName='Alexander', lastName='Filipenko'}";
    private static final  String BOOKLISTSTRING = "[Book{id=7, name='Red Cross', author=Filipenko, genre=Historical Drama}]";

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
        given(authorCUService.create()).willReturn(new Author("Dan","Simmons"));
    }

    @Test
    public void create() {
        assertEquals(AUTHORSTRING,appCommands.create("author"));
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
        given(ioService.userInput(any())).willReturn("77");
        String commandResult = appCommands.getBooksByAuthor();
        assertEquals(BOOKLISTSTRING,commandResult);
    }

    @Test
    public void getBooksByGenre() {
        given(ioService.userInput(any())).willReturn("777");
        String commandResult = appCommands.getBooksByGenre();
        assertEquals(BOOKLISTSTRING,commandResult);
    }

//    @Test
//    public void getAuthorsByGenreName() {
//        given(ioService.userInput(any())).willReturn("Historical Drama");
//        String commandResult = appCommands.getAuthorsByGenreName();
//        assertEquals("["+AUTHORSTRING3+"]",commandResult);
//    }
    @Test
    public void count() {
        assertEquals("Total count of 'author' : 3",appCommands.count("author"));
    }
}