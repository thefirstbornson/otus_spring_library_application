package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
