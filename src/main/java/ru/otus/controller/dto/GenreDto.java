package ru.otus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    String genre;
    public static GenreDto toDto(String genre)
    {
        return new GenreDto(genre);
    };
}
