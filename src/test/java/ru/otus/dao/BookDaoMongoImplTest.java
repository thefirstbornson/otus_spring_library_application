package ru.otus.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

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
public class BookDaoMongoImplTest {
    private static final String NAME = "Anna Karenina";
    private static final String NAME2 = "Snuff";
    private static final String NAME3 = "Red Cross";
    private static final String NAME4 = "Aristonomia";

    @Autowired
    BookRepository bookRepository;

    @Test
    public void getCountTest(){
        assertEquals(3L,(long) bookRepository.count());
    }

    @Test
    public void findAllTest(){
        Book book = new Book(NAME, "Leo Tolstoy", "Drama", "Novel");

        Book[] bookArr = {book,new Book(NAME2, "Viktor Pelevin", "Sci-fi","Novel")
                ,new Book(NAME3, "Alexander Filipenko","Historical Drama","Novel")};
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
        Book book = new Book("1",NAME, "Leo Tolstoy", "Childhood", "Novel");
        bookRepository.save(book);
        assertTrue(book.getId()!=null);
        bookRepository.delete(book);
    }

    @Test
    public void shouldRaiseIllegalArgumentException() throws IllegalArgumentException {
        Book book = new Book(NAME, "Leo Tolstoy", "Childhood", "Novel");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookRepository.findById(book.getId()).get();
        });
    }

    @Test
    public void findByIdTest(){
        Book book = new Book("1",NAME, "Leo Tolstoy", "Childhood", "Novel");
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById("1");
        assertEquals(NAME,result.get().getName());
        bookRepository.delete(book);
    }


    @Test
    public void updateTest(){
        Book book = new Book("1",NAME, "Leo Tolstoy", "Childhood", "Novel");
        bookRepository.save(book);
        book.setName(NAME4);
        bookRepository.save(book);
        assertEquals(NAME4, bookRepository.findById(book.getId()).get().getName());
        bookRepository.delete(book);
    }

    @Test
    public void deleteTest(){
        Book book = new Book("1",NAME, "Leo Tolstoy", "Childhood", "Novel");
        bookRepository.save(book);
        String id = book.getId();
        bookRepository.delete(book);
        assertTrue(!bookRepository.findById(id).isPresent());
    }

}