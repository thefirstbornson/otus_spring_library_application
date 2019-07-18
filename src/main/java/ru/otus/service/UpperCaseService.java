package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;

@Service
public class UpperCaseService {
    public Book upperBook(Book book) throws Exception {
        book.setName(book.getName().toUpperCase());
        return book;
    }
}
