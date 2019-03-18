package ru.otus.domain;

public class Genre {
    private long genreCode;
    private String genreName;

    public Genre(long genreCode, String genreName) {
        this.genreCode = genreCode;
        this.genreName = genreName;
    }

    public long getGenreCode() {
        return genreCode;
    }

    public void setGenreCode(long genreCode) {
        this.genreCode = genreCode;
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

        if (genreCode != genre.genreCode) return false;
        return genreName.equals(genre.genreName);
    }

    @Override
    public int hashCode() {
        int result = (int) (genreCode ^ (genreCode >>> 32));
        result = 31 * result + genreName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreCode=" + genreCode +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
