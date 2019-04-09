package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
