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
@Document(collection = "bookcomment")
public class BookCommentDoc {
    @Id
    private String id;
    private String comment;
    private String book;

    public BookCommentDoc(String comment, Book book) {
        this.comment = comment;
        this.book = book.getAuthor().getLastName() + " " +book.getAuthor().getFirstName()
            + " " + book.getName();
    }
}