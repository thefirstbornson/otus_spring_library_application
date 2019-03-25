package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;

@Repository
public class AuthorDaoJDBCImpl extends GenericDaoJDBCImpl<Author> implements AuthorDao {

    public AuthorDaoJDBCImpl(NamedParameterJdbcOperations jdbcOperations) {
        super("author", jdbcOperations);
    }

    @Override
    public Author save(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("firstN", author.getFirstName())
                .addValue("lastN", author.getLastName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("insert into %s (FIRST_NAME, LAST_NAME) values (:firstN, :lastN)"
                                    , this.getTableName()),parameters,keyHolder,new String[]{"id"});
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("authorId",author.getId())
                .addValue("firstN", author.getFirstName())
                .addValue("lastN", author.getLastName());
        jdbcOperations.update(String.format("update %s set FIRST_NAME=:firstN, LAST_NAME=:lastN " +
                        " where id =:authorId",this.getTableName())
                , parameters);
    }

    @Override
    public void delete(Author author) {
        Map<String, Object> params = Collections.singletonMap("id", author.getId());
        jdbcOperations.update(String.format("delete from %s where id = :id",this.getTableName()), params);
    }

    @Override
    public RowMapper<Author> getRowMapper() {
        return (ResultSet result, int rowNum) -> {
                Author author = new Author();
                author.setId(result.getLong("ID"));
                author.setFirstName(result.getString("FIRST_NAME"));
                author.setLastName(result.getString("LAST_NAME"));
                return author;
        };
    }

}
