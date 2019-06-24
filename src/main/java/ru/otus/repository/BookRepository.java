package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBooksByAuthor (Author author);
    List<Book> findBooksByGenre (Genre genre);

    @Query("select distinct b.author from Book b join b.author join b.genre where b.genre.genreName = ?1")
    List<Author> findAuthorByGenreGenreName (String genreName);

    @EntityGraph("bookGraph")
    @Override
    List<Book>findAll();
}
