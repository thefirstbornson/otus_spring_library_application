package ru.otus.controller.dto;

import java.text.ParseException;

public interface DtoConversion<T,D> {
    D convertToDto(T instance, Class<D> dtoClass);

    T convertToEntity(D dto, Class<T> entity) throws ParseException ;
}
