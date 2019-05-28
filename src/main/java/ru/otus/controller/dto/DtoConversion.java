package ru.otus.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public class DtoConversion<T, D> {
    private final ModelMapper modelMapper;

    @Autowired
    public DtoConversion(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D convertToDto(T instance, Class<D> dtoClass) {
        return modelMapper.map(instance, dtoClass);
    }

    private T convertToEntity(D dto, Class<T> entity) throws ParseException {
        return modelMapper.map(dto,entity);
    }
}
