package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorDataJDBCImpl extends GenericDaoJDBCImpl<Author> implements AuthorDao {
    private final String tableName ="author";

    public AuthorDataJDBCImpl(NamedParameterJdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }

    @Override
    public Author save(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstN",author.getAuthor_first_name());
        params.put("lastN",author.getAuthor_last_name());
        long id = (long) jdbcOperations.update("insert into "+getTableName()
                        +" (FIRST_NAME, LAST_NAME) values (:firstN, :lastN) RETURNING id"
                , params);
        author.setId(id);
        return author;
    }

    @Override
    public void update(Author object) {

    }

    @Override
    public void delete(Author object) {

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

    @Override
    public String getTableName() {
        return tableName;
    }
}
