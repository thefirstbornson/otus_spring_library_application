package ru.otus.shell;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;
import ru.otus.instance_service.AuthorCUService;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ComponentScan
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource( "classpath:test-application.properties")
@Transactional
public class AppCommandsTest {
    @MockBean
    ShellInputMatcher shellInputMatcher;
    @MockBean
    AuthorCUService authorCUService;

    @Autowired
    AuthorDao authorDao;
    @Autowired
    AppCommands appCommands;

    @Before
    public void setUp(){
        given(shellInputMatcher.getDao(any())).willReturn(authorDao);
        given(shellInputMatcher.getServise(any())).willReturn(authorCUService);
        given(authorCUService.create()).willReturn(new Author("Dan","Simmons"));
    }

    @Test
    public void create() {
        assertEquals("Author{id=1, firstName='Dan', lastName='Simmons'}"
                ,appCommands.create("author"));
    }

    @Test
    public void showAll() {
        assertEquals("[Author{id=99, firstName='Fedor', lastName='Dostoevsky'}" +
                ", Author{id=88, firstName='Viktor', lastName='Pelevin'}" +
                ", Author{id=77, firstName='Alexander', lastName='Filipenko'}]"
                ,appCommands.showAll("author"));
    }

    @Test
    public void count() {
        assertEquals("Total count of 'author' : 3",appCommands.count("author"));
    }
}