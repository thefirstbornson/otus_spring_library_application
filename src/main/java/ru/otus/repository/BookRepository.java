package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String> {
    List<Book> findBooksByAuthor (String author);
    List<Book> findBooksByGenre (String genre);

    @Query("'genre'")
    List<String> findAuthorByGenreGenreName (String genreName);

   // @EntityGraph("bookGraph")
//    @Override
//    List<Book>findAll();
}
