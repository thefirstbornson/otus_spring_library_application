package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
