package ru.otus.dao;

import java.util.List;

public interface GenericDao<T> {
    T findById(long id);

    List<T> findAll();

    Long getCount();

    T save(T entity);

    void update(T object) ;

    void delete(T object);
}
