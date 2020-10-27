package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;

import java.util.List;

public interface MediaDao extends BaseDao<Film> {
    Film findFilmByName(String name) throws DaoException;

    List<Film> findAllUndeletedFilms(int currentPage, int filmsOnPage) throws DaoException;

    int calculateNumberOfRows() throws DaoException;

    Film findFilmById(long id) throws DaoException;

    FilmInfo findInfoById(long filmId) throws DaoException;

    boolean isFilmExist(String filmName) throws DaoException;

    public boolean createFilmInfo(FilmInfo filminfo) throws DaoException;
}
