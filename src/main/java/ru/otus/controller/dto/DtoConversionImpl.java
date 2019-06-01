package ru.otus.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class DtoConversionImpl<T, D>  implements DtoConversion<T, D>  {
    private final ModelMapper modelMapper;

    @Autowired
    public DtoConversionImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public D convertToDto(T instance, Class<D> dtoClass) {
        return modelMapper.map(instance, dtoClass);
    }
    @Override
    public T convertToEntity(D dto, Class<T> entity) throws ParseException {
        return modelMapper.map(dto,entity);
    }

}
