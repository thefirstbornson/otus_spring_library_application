package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String name;
    private String author;
    private String genre;
    private String literaryForm;

    public Book(String name, String author, String genre, String literaryForm) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.literaryForm = literaryForm;
    }
}
