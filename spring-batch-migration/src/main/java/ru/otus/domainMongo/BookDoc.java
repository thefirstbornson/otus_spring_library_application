package ru.otus.domainMongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.domain.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class BookDoc {
    @Id
    private String id;
    private String name;
    private String author;
    private String genre;
    private String literaryForm;

    public BookDoc(String name, String author, String genre, String literaryForm) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.literaryForm = literaryForm;
    }

    public BookDoc(Book book) {
        this.name = book.getName();
        this.author = book.getAuthor().getFirstName() + book.getAuthor().getLastName();
        this.genre = book.getGenre().getGenreName();
        this.literaryForm = "";
    }
}
