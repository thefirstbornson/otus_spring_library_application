package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.domain.Author;

import java.sql.ResultSet;

public class AuthorDataJDBCImpl extends GenericDaoJDBCImpl<Author> implements AuthorDao {

    @Override
    public RowMapper<Author> createRowMapper() {
        return (ResultSet result, int rowNum) -> {
                Author author = new Author();
                author.setAuthor_id(result.getLong("AUTHOR_ID"));
                author.setAuthor_first_name(result.getString("FIRST_NAME"));
                author.setAuthor_last_name(result.getString("LAST_NAME"));
                return author;
        };
    }
}
