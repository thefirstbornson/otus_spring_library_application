package ru.otus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCommentDto {
    private long id;
    private String comment;
    private BookDto book;
}
