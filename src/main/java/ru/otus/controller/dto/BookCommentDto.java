package ru.otus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCommentDto {
    private String id;
    private String comment;
    private String book;

    public static BookCommentDto toDto(String id, String comment,String book)
    {
        return new BookCommentDto(id,comment,book);
    };
}
