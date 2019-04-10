package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBooksByAuthor (Author author);
    List<Book> findBooksByGenre (Genre genre);
}
