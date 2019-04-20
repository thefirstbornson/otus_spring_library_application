package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "author")
public class Author {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String yearsOfLife;

    public Author(String firstName, String lastName, String yearsOfLife) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsOfLife = yearsOfLife;
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
