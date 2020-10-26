package by.epam.project.entity.impl;

import by.epam.project.entity.Entity;

public class FilmInfo implements Entity {
    private String description;
    private int yearOfCreation;
    private String genre;
    private String link;
    private long filmId;

    public FilmInfo(String description, int yearOfCreation, String genre, long filmId, String link) {
        this.description = description;
        this.yearOfCreation = yearOfCreation;
        this.genre = genre;
        this.filmId = filmId;
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmInfo)) return false;
        if (!super.equals(o)) return false;

        FilmInfo filmInfo = (FilmInfo) o;

        if (getYearOfCreation() != filmInfo.getYearOfCreation()) return false;
        if (getFilmId() != filmInfo.getFilmId()) return false;
        if (getDescription() != null ? !getDescription().equals(filmInfo.getDescription()) : filmInfo.getDescription() != null)
            return false;
        if (getGenre() != null ? !getGenre().equals(filmInfo.getGenre()) : filmInfo.getGenre() != null) return false;
        return getLink() != null ? getLink().equals(filmInfo.getLink()) : filmInfo.getLink() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getYearOfCreation();
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (getLink() != null ? getLink().hashCode() : 0);
        result = 31 * result + (int) (getFilmId() ^ (getFilmId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("FilmInfo{").append("description='").append(description)
                .append('\'').append(", yearOfCreation=").append(yearOfCreation)
                .append(", genre='").append(genre).append('\'').append(", filmId=")
                .append(filmId).append(", link=").append(link).append('}').toString();
    }
}
