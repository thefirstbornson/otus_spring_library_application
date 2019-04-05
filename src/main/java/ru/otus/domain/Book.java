package ru.otus.domain;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
@BatchSize(size=100)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Book(String name,Author author, Genre genre) {
        this.name=name;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        String auth = author!=null ? author.getLastName():"";
        String gen = genre!=null? genre.getGenreName():"";
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + auth +
                ", genre=" + gen +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        return genre != null ? genre.equals(book.genre) : book.genre == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

}
