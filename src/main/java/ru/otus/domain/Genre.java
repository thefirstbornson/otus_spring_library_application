package ru.otus.domain;

public class Genre {
    private long id;
    private String genreName;

    public Genre(long id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        return genreName.equals(genre.genreName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + genreName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
