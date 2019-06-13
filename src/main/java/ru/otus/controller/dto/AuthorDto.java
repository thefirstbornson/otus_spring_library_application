package ru.otus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    String author;

    public static AuthorDto toDto(String author)
    {
        return new AuthorDto(author);
    };
}
