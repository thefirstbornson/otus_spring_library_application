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

import ru.otus.domain.Book;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan({"ru.otus.dao"})
@TestPropertySource("classpath:application-test.properties")
public class BookDaoJpaImplTest {
    private static final String NAME = "Anna Karenina";
    private static final String NAME2 = "Snuff";
    private static final String NAME3 = "Red Cross";
    private static final String NAME4 = "Aristonomia";

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GenreRepository genreRepository;
    Book book;

    @BeforeEach
    public void setUp() throws Exception {
        book = new Book(NAME
                , authorRepository.findById(99L).get()
                , genreRepository.findById(999L).get()
        );
    }

    @Test
    public void getCountTest(){
        assertEquals(3L,(long) bookRepository.count());
    }

    @Test
    public void findAllTest(){
        Book[] bookArr = {book,new Book(NAME2, authorRepository.findById(88L).get(), genreRepository.findById(888L).get())
                ,new Book(NAME3, authorRepository.findById(77L).get(), genreRepository.findById(777L).get())};
        List<String> testBooksNames = Arrays.asList(bookArr)
                .stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        List<String> dbBookNames = bookRepository.findAll().stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
        assertThat(testBooksNames).containsExactlyInAnyOrderElementsOf(dbBookNames);
    }

    @Test
    public void saveTest(){
        bookRepository.save(book);
        assertTrue(book.getId()>0);
    }

    @Test
    public void shouldRaiseAnNoSuchElementException() throws NoSuchElementException {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            bookRepository.findById(book.getId()).get();
        });
    }

    @Test
    public void findByIdTest(){
        Book result = bookRepository.findById(9L).get();
        assertEquals(NAME,result.getName());
    }


    @Test
    public void updateTest(){

        book= bookRepository.findById(9L).get();
        book.setName(NAME4);
        bookRepository.save(book);
        assertEquals(NAME4, bookRepository.findById(book.getId()).get().getName());
    }

    @Test
    public void deleteTest(){
        bookRepository.save(book);
        Long id = book.getId();
        bookRepository.delete(book);
        assertTrue(!bookRepository.findById(id).isPresent());
    }

}