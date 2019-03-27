package ru.otus.domain;

import javax.persistence.*;

@Entity
@Table(name="book_comment")
public class BookComment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    public BookComment() {
    }

    public BookComment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", book=" + book.getName() +
                '}';
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
