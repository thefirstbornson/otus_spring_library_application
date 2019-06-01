package ru.otus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;
}
