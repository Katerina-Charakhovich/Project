package by.epam.project.entity.impl;


import by.epam.project.entity.Entity;

public class PurchasedFilm implements Entity {
    private long idUser;
    private long filmId;

    public PurchasedFilm(long idUser, long filmId) {
        this.idUser = idUser;
        this.filmId = filmId;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchasedFilm)) return false;

        PurchasedFilm that = (PurchasedFilm) o;

        if (getIdUser() != that.getIdUser()) return false;
        return getFilmId() == that.getFilmId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getIdUser() ^ (getIdUser() >>> 32));
        result = 31 * result + (int) (getFilmId() ^ (getFilmId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("PurchasedFilm{").append("idUser=").append(idUser)
                .append(", filmId=").append(filmId).append('}').toString();
    }
}
