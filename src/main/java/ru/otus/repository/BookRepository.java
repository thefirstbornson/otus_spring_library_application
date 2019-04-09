package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
