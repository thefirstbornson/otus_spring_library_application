package ru.otus.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.otus.domain.Author;
import ru.otus.exception.NoEntityException;
import ru.otus.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan({"ru.otus.dao"})
@TestPropertySource("classpath:application-test.properties")
public class AuthorRepoJpaImplTest {

    private static final String NAME1 = "Fedor";
    private static final String SURNAME1 = "Dostoevsky";
    private static final String NAME2 = "Viktor";
    private static final String SURNAME2 = "Pelevin";
    private static final String NAME3 = "Alexander";
    private static final String SURNAME3 = "Filipenko";
    private static final String NAME4 = "Boris";
    private static final String SURNAME4 = "Akunin";

    @Autowired
    AuthorRepository authorDataJpa;
    Author author;

    @BeforeEach
    public void setUp(){
        author = new Author(NAME1, SURNAME1);

    }

    @Test
    public void getCountTest(){
        assertEquals(3L,(long) authorDataJpa.count());
    }

    @Test
    public void findAllTest(){
        String [] testFirstNamesArr = {NAME1,NAME2,NAME3};
        List<String> testFirstNamesList = Arrays.asList(testFirstNamesArr);

        List<String> dbAuthorsNames = authorDataJpa.findAll().stream()
                                         .map(Author::getFirstName)
                                         .collect(Collectors.toList());

        assertThat(testFirstNamesList).containsExactlyInAnyOrderElementsOf(dbAuthorsNames);
    }

    @Test
    public void saveTest(){
        authorDataJpa.save(author);
        assertTrue(author.getId()>0);
    }

    @Test
    public void shouldRaiseAnNoSuchElementException() throws NoSuchElementException {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            authorDataJpa.findById(author.getId()).get();
        });

    }

    @Test
    public void findByIdTest(){
        Optional<Author> result = authorDataJpa.findById(99L);
        assertEquals(NAME1,result.get().getFirstName());
        assertEquals(SURNAME1,result.get().getLastName());
    }


    @Test
    public void updateTest(){
        author= authorDataJpa.findById(99L).get();
        author.setFirstName(NAME4);
        author.setLastName(SURNAME4);
        authorDataJpa.save(author);
        assertEquals(NAME4,authorDataJpa.findById(author.getId()).get().getFirstName());
        assertEquals(SURNAME4,authorDataJpa.findById(author.getId()).get().getLastName());
    }

    @Test
    public void deleteTest(){
        authorDataJpa.save(author);
        long id = author.getId();
        authorDataJpa.delete(author);
       assertTrue(!authorDataJpa.findById(id).isPresent());
    }
}