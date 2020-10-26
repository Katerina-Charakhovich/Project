package by.epam.project.entity.impl;

import by.epam.project.entity.Entity;

public class Film implements Entity {
    private long filmId;
    private String filmName;
    private String realName;
    private FilmInfo filmInfo;

    public Film(long filmId, String filmName, String realName,FilmInfo filmInfo) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.realName = realName;
        this.filmInfo=filmInfo;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public FilmInfo getFilmInfo() {
        return filmInfo;
    }

    public void setFilmInfo(FilmInfo filmInfo) {
        this.filmInfo = filmInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;

        Film film = (Film) o;

        if (getFilmId() != film.getFilmId()) return false;
        if (getFilmName() != null ? !getFilmName().equals(film.getFilmName()) : film.getFilmName() != null)
            return false;
        if (getRealName() != null ? !getRealName().equals(film.getRealName()) : film.getRealName() != null)
            return false;
        return getFilmInfo() != null ? getFilmInfo().equals(film.getFilmInfo()) : film.getFilmInfo() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getFilmId() ^ (getFilmId() >>> 32));
        result = 31 * result + (getFilmName() != null ? getFilmName().hashCode() : 0);
        result = 31 * result + (getRealName() != null ? getRealName().hashCode() : 0);
        result = 31 * result + (getFilmInfo() != null ? getFilmInfo().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Film{").append("filmName='").append(filmName).append('\'')
                .append(", realName='").append(realName).append('\'')
                .append(", filmId='").append(filmId).append('\'')
                .append(", filmInfo='").append(filmInfo).append('\'')
                .append('\'').append('}').toString();
    }
}
