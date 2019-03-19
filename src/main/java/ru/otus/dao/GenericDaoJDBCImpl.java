package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericDaoJDBCImpl<T> implements GenericDao<T> {
    private final String tableName;

    protected final NamedParameterJdbcOperations jdbcOperations;

    protected GenericDaoJDBCImpl(String tableName, NamedParameterJdbcOperations jdbcOperations) {
        this.tableName = tableName;
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public T findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject(
                "select * from "+getTableName()+" where id = :id", params, getRowMapper());
    }

    @Override
    public List<T> findAll() {
        return jdbcOperations.query("select * from "+getTableName(),getRowMapper());
    }

    @Override
    public Long getCount() {
        return jdbcOperations.queryForObject("select count(*) from "+getTableName(),new HashMap<>(),Long.class);
    }

    @Override
    public abstract T save(T entity);

    @Override
    public abstract void update(T object) ;

    @Override
    public abstract void delete(T object);

    public abstract RowMapper<T> getRowMapper();

    public String getTableName(){
        return tableName;
    }



}
