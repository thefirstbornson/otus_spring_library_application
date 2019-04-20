package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String> {
    List<Book> findBooksByAuthor (String author);
    List<Book> findBooksByGenre (String genre);
    List<Book> findBooksByLiteraryForm (String literaryForm);

    @Query(value="{ 'genre' : ?0 }", fields="{_id: 0,name:0, genre:0,literaryForm:0, _class:0}")
    List<String> findAuthorByGenreGenreName (String genreName);

}
