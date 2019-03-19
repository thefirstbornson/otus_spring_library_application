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
                .addValue("firstN", author.getAuthor_first_name())
                .addValue("lastN", author.getAuthor_last_name());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into "+this.getTableName() +" (FIRST_NAME, LAST_NAME) values (:firstN, :lastN)"
                ,parameters,keyHolder,new String[]{"id"});
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("authorId",author.getId())
                .addValue("firstN", author.getAuthor_first_name())
                .addValue("lastN", author.getAuthor_last_name());
        jdbcOperations.update("update "+this.getTableName()
                               +" set FIRST_NAME=:firstN, LAST_NAME=:lastN "
                               +" where id =:authorId"
                , parameters);
    }

    @Override
    public void delete(Author author) {
        Map<String, Object> params = Collections.singletonMap("id", author.getId());
        jdbcOperations.update(
                "delete from "+this.getTableName()+" where id = :id", params
        );
    }

    @Override
    public RowMapper<Author> getRowMapper() {
        return (ResultSet result, int rowNum) -> {
                Author author = new Author();
                author.setId(result.getLong("ID"));
                author.setAuthor_first_name(result.getString("FIRST_NAME"));
                author.setAuthor_last_name(result.getString("LAST_NAME"));
                return author;
        };
    }

}
