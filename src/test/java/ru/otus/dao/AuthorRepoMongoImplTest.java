package ru.otus.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.testconfig", "ru.otus.repository"})
public class AuthorRepoMongoImplTest {

    private static final String ID0 = "1";
    private static final String NAME0 = "Dmytry";
    private static final String SURNAME0 = "Bykov";
    private static final String YEARS0 = "1960-";
    private static final String NAME1 = "Fedor";
    private static final String SURNAME1 = "Dostoevsky";
    private static final String NAME2 = "Viktor";
    private static final String SURNAME2 = "Pelevin";
    private static final String NAME3 = "Alexander";
    private static final String SURNAME3 = "Filipenko";
    private static final String NAME4 = "Boris";
    private static final String SURNAME4 = "Akunin";

    @Autowired
    AuthorRepository authorDataMongo;



    @Test
    public void getCountTest(){
        assertEquals(3L,(long) authorDataMongo.count());
    }

    @Test
    public void findAllTest(){
        String [] testFirstNamesArr = {NAME1,NAME2,NAME3};
        List<String> testFirstNamesList = Arrays.asList(testFirstNamesArr);

        List<String> dbAuthorsNames = authorDataMongo.findAll().stream()
                                         .map(Author::getFirstName)
                                         .collect(Collectors.toList());

        assertThat(testFirstNamesList).containsExactlyInAnyOrderElementsOf(dbAuthorsNames);
    }

    @Test
    public void saveTest(){
        Author author = new Author(ID0,NAME0,SURNAME0, YEARS0);
        author = authorDataMongo.save(author);
        assertTrue(author.getId()!=null);
        authorDataMongo.delete(author);
    }

    @Test
    public void shouldRaiseAnIllegalArgumentException() throws IllegalArgumentException {
        Author author = new Author(NAME0,SURNAME0, YEARS0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            authorDataMongo.findById(author.getId()).get();
        });

    }

    @Test
    public void findByIdTest(){
        Author author = new Author(ID0,NAME0,SURNAME0, YEARS0);
        author = authorDataMongo.save(author);
        Optional<Author> result = authorDataMongo.findById(ID0);
        assertEquals(NAME0,result.get().getFirstName());
        assertEquals(SURNAME0,result.get().getLastName());
        authorDataMongo.delete(author);
    }


    @Test
    public void updateTest(){
        Author author = new Author(ID0,NAME0,SURNAME0, YEARS0);
        author = authorDataMongo.save(author);
        author.setFirstName(NAME4);
        author.setLastName(SURNAME4);
        authorDataMongo.save(author);
        assertEquals(NAME4, authorDataMongo.findById(author.getId()).get().getFirstName());
        assertEquals(SURNAME4, authorDataMongo.findById(author.getId()).get().getLastName());
        authorDataMongo.delete(author);
    }

    @Test
    public void deleteTest(){
        Author author = new Author(ID0,NAME0,SURNAME0, YEARS0);
        authorDataMongo.save(author);
        String id = author.getId();
        authorDataMongo.delete(author);
        assertTrue(!authorDataMongo.findById(id).isPresent());
    }

}