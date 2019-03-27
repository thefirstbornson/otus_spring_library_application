package ru.otus.domain;

import javax.persistence.*;

@Entity
public class BookComment{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    public BookComment() {
    }

    public BookComment(String comment) {
        this.comment = comment;
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
                '}';
    }
}
