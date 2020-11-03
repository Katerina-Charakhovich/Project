package by.epam.project.entity.impl;

import by.epam.project.entity.Entity;

public class Film implements Entity {
    private long filmId;
    private String filmName;
    private String filmAvatar;
    private FilmInfo filmInfo;
    private boolean active;

    public Film(long filmId, String filmName, String filmAvatar, FilmInfo filmInfo) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmAvatar = filmAvatar;
        this.filmInfo = filmInfo;
    }

    public Film() {

    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmAvatar() {
        return filmAvatar;
    }

    public void setFilmAvatar(String filmAvatar) {
        this.filmAvatar = filmAvatar;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;

        Film film = (Film) o;

        if (getFilmId() != film.getFilmId()) return false;
        if (isActive() != film.isActive()) return false;
        if (getFilmName() != null ? !getFilmName().equals(film.getFilmName()) : film.getFilmName() != null)
            return false;
        if (getFilmAvatar() != null ? !getFilmAvatar().equals(film.getFilmAvatar()) : film.getFilmAvatar() != null)
            return false;
        return getFilmInfo() != null ? getFilmInfo().equals(film.getFilmInfo()) : film.getFilmInfo() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getFilmId() ^ (getFilmId() >>> 32));
        result = 31 * result + (getFilmName() != null ? getFilmName().hashCode() : 0);
        result = 31 * result + (getFilmAvatar() != null ? getFilmAvatar().hashCode() : 0);
        result = 31 * result + (getFilmInfo() != null ? getFilmInfo().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Film{").append("filmName='").append(filmName).append('\'')
                .append(", realName='").append(filmAvatar).append('\'')
                .append(", filmId='").append(filmId).append('\'')
                .append(", filmInfo='").append(filmInfo).append('\'')
                .append(", active='").append(active).append('\'')
                .append('\'').append('}').toString();
    }
}
