package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class GenericDaoJDBCImpl<T> implements GenericDao<T> {

    private RowMapper<T> rowMapper;

    @Override
    public T findById(long id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public Long getCount() {
        return null;
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public void update(T object) {

    }

    @Override
    public void delete(T object) {

    }

    public RowMapper<T> getRowMapper() {
        return (rowMapper == null)
                ? createRowMapper()
                :rowMapper;
    }

    public abstract RowMapper<T> createRowMapper();

}
