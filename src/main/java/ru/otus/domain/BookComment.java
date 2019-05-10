package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookcomment")
public class BookComment{
    @Id
    private String id;
    private String comment;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="book_id")
    @DBRef
    private Book book;

    public BookComment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }
}
