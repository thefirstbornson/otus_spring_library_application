package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;

@Repository
public class GenreDaoJDBCImpl extends GenericDaoJDBCImpl<Genre> implements GenreDao {


    protected GenreDaoJDBCImpl(NamedParameterJdbcOperations jdbcOperations) {
        super("genre", jdbcOperations);
    }

    @Override
    public Genre save(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("genreN", genre.getGenreName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(String.format("insert into %s (NAME) values (:genreN)",this.getTableName())
                ,parameters,keyHolder,new String[]{"id"});
        genre.setId(keyHolder.getKey().longValue());
        return genre;
    }

    @Override
    public void update(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("genreId",genre.getId())
                .addValue("genreN", genre.getGenreName());
        jdbcOperations.update(String.format("update %s set NAME=:genreN"
                        +" where id =:genreId",this.getTableName())
                , parameters);
    }

    @Override
    public void delete(Genre genre) {
        Map<String, Object> params = Collections.singletonMap("id", genre.getId());
        jdbcOperations.update(String.format("delete from %s where id = :id",this.getTableName()), params );
    }

    @Override
    public RowMapper<Genre> getRowMapper() {
        return (ResultSet result, int rowNum) -> {
            Genre genre = new Genre();
            genre.setId(result.getLong("ID"));
            genre.setGenreName(result.getString("NAME"));
            return genre;
        };
    }

}
