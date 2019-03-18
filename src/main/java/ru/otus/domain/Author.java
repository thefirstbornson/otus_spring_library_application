package ru.otus.domain;

public class Author {

    private long author_id;
    private String author_first_name;
    private String author_last_name;

    public Author(long author_id, String author_first_name, String author_last_name) {
        this.author_id = author_id;
        this.author_first_name = author_first_name;
        this.author_last_name = author_last_name;
    }

    public Author() {
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_first_name() {
        return author_first_name;
    }

    public void setAuthor_first_name(String author_first_name) {
        this.author_first_name = author_first_name;
    }

    public String getAuthor_last_name() {
        return author_last_name;
    }

    public void setAuthor_last_name(String author_last_name) {
        this.author_last_name = author_last_name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (author_id != author.author_id) return false;
        if (!author_first_name.equals(author.author_first_name)) return false;
        return author_last_name.equals(author.author_last_name);
    }

    @Override
    public int hashCode() {
        int result = (int) (author_id ^ (author_id >>> 32));
        result = 31 * result + author_first_name.hashCode();
        result = 31 * result + author_last_name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "author_id=" + author_id +
                ", author_first_name='" + author_first_name + '\'' +
                ", author_last_name='" + author_last_name + '\'' +
                '}';
    }
}
