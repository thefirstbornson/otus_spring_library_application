package ru.otus.dao;

import java.util.List;

interface GenericDao<T> {
    T findById(long id);

    List<T> findAll();

    Long getCount();

    T save(T entity);

    public void update(T object) ;

    public void delete(T object);
}
