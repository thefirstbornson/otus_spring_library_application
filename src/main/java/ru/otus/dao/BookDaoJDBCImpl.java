package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;

@Repository
public class BookDaoJDBCImpl  extends GenericDaoJDBCImpl<Book> implements BookDao {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    protected BookDaoJDBCImpl(NamedParameterJdbcOperations jdbcOperations, AuthorDao authorDao, GenreDao genreDao) {
        super("book", jdbcOperations);
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book save(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId",book.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into "+this.getTableName()
                        +" (NAME, AUTHOR_ID, GENRE_ID) values (:name, :authorId, :genreId)"
                ,parameters,keyHolder,new String[]{"id"});
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",book.getId())
                .addValue("name", book.getName())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId",book.getGenre().getId());
        jdbcOperations.update("update "+this.getTableName()
                        +" set NAME=:name, AUTHOR_ID=:authorId, GENRE_ID=:genreId "
                        +" where id =:id"
                , parameters);

    }

    @Override
    public void delete(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        jdbcOperations.update(
                "delete from "+this.getTableName()+" where id = :id", params
        );
    }

    @Override
    public RowMapper<Book> getRowMapper() {
        return (ResultSet result, int rowNum) -> {
            Book book = new Book();
            book.setId(result.getLong("ID"));
            book.setName(result.getString("NAME"));
            book.setAuthor(authorDao.findById(result.getLong("AUTHOR_ID")));
            book.setGenre(genreDao.findById(result.getLong("GENRE_ID")));
            return book;
        };
    }
}
