package by.epam.project.entity.impl;

import by.epam.project.entity.Entity;

public class Film extends Entity {
    private String filmName;
    private String realName;

    public Film(long id, String filmName, String realName) {
        super(id);
        this.filmName = filmName;
        this.realName = realName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        if (!super.equals(o)) return false;

        Film film = (Film) o;

        if (getFilmName() != null ? !getFilmName().equals(film.getFilmName()) : film.getFilmName() != null)
            return false;
        return getRealName() != null ? getRealName().equals(film.getRealName()) : film.getRealName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getFilmName() != null ? getFilmName().hashCode() : 0);
        result = 31 * result + (getRealName() != null ? getRealName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Film{").append("filmName='").append(filmName).append('\'')
                .append(", realName='").append(realName).append('\'')
                .append('\'').append('}').toString();
    }
}
