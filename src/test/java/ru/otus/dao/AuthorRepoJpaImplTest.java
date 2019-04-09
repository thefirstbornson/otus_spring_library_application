package ru.otus.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.domain.Author;
import ru.otus.exception.NoEntityException;
import ru.otus.repository.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
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

    @Before
    public void setUp(){
        author = new Author(NAME1, SURNAME1);

    }

    @Test
    public void getCountTest(){
        Assert.assertEquals(3L,(long) authorDataJpa.count());
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
        Assert.assertTrue(author.getId()>0);
    }

    @Test(expected = NoEntityException.class)
    public void notFoundByIdTest() throws NoEntityException {
       authorDataJpa.findById(author.getId()).orElseThrow(NoEntityException::new);
    }

    @Test
    public void findByIdTest(){
        Optional<Author> result = authorDataJpa.findById(99L);
        Assert.assertTrue(result.get().getFirstName().equals(NAME1));
        Assert.assertTrue(result.get().getLastName().equals(SURNAME1));
    }


    @Test
    public void updateTest(){
        author= authorDataJpa.findById(99L).get();
        author.setFirstName(NAME4);
        author.setLastName(SURNAME4);
        authorDataJpa.save(author);
        Assert.assertTrue( authorDataJpa.findById(author.getId())
                .get().getFirstName().equals(NAME4));
        Assert.assertTrue( authorDataJpa.findById(author.getId())
                .get().getLastName().equals(SURNAME4));
    }

    @Test
    public void deleteTest(){
        authorDataJpa.save(author);
        long id = author.getId();
        authorDataJpa.delete(author);
        Assert.assertTrue(!authorDataJpa.findById(id).isPresent());
    }
}